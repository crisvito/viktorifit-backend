package com.viktoria.viktorifit.user.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.viktoria.viktorifit.user.dto.ChangePasswordDTO;
import com.viktoria.viktorifit.user.dto.UserAuthDTO;
import com.viktoria.viktorifit.user.dto.UserDTO;
import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.entity.UserProfileEntity;
import com.viktoria.viktorifit.user.enums.RoleEnum;
import com.viktoria.viktorifit.user.repository.UserRepository;
import com.viktoria.viktorifit.utility.JwtUtil;
import com.viktoria.viktorifit.utility.email.service.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final UserProfileService userProfileService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Value("${app.frontend.url}")
  private String baseUrl;

  public UserEntity toEntity(UserDTO userDTO) {
    return UserEntity.builder()
        .id(userDTO.getId())
        .fullname(userDTO.getFullname())
        .username(userDTO.getUsername())
        .email(userDTO.getEmail())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .createdAt(userDTO.getCreatedAt())
        .updatedAt(userDTO.getUpdatedAt())
        .build();
  }

  public UserDTO toDTO(UserEntity userEntity) {
    return UserDTO.builder()
        .id(userEntity.getId())
        .fullname(userEntity.getFullname())
        .username(userEntity.getUsername())
        .email(userEntity.getEmail())
        .userProfileDTO(userEntity.getUserProfile() != null 
                ? userProfileService.toDTO(userEntity.getUserProfile()) 
                : null)
        .role(userEntity.getRole().name())
        .createdAt(userEntity.getCreatedAt())
        .updatedAt(userEntity.getUpdatedAt())
        .build();
  }

  @Transactional
  public void cleanupExpiredInactiveUsers() {

      var expiredUsers = userRepository
          .findByIsActiveFalseAndActivationExpiredAtBeforeAndIsDeletedFalse(
              LocalDateTime.now()
          );

      for (UserEntity user : expiredUsers) {
          user.setIsDeleted(true);
          user.setDeletedAt(LocalDateTime.now());
          user.setEmail("deleted_" + user.getId() + "_" + user.getEmail());

          userRepository.save(user);
      }
  }

  public UserDTO registerProfile(UserDTO userDTO) {

    if (userRepository.existsByEmailAndIsDeletedFalse(userDTO.getEmail())) {
        throw new RuntimeException("Email already registered");
    }
    if (userRepository.existsByUsernameAndIsDeletedFalse(userDTO.getUsername())) {
        throw new RuntimeException("Username already taken");
    }

    UserEntity newUser = toEntity(userDTO);
    newUser.setRole(RoleEnum.USER);
    newUser.setActivationToken(UUID.randomUUID().toString());
    newUser.setActivationExpiredAt(LocalDateTime.now().plusHours(1));
    newUser.setIsActive(false);
    newUser = userRepository.save(newUser);
    
    String activationLink = baseUrl + "/auth/activate?token=" + newUser.getActivationToken();
    String subject = "Activate your Viktorifit account";
    String body = "Click on the following link to activate your account: " + activationLink;
    emailService.sendEmail(newUser.getEmail(), subject, body);
    return toDTO(newUser);
}

  public boolean activateProfile(String activationToken) {
    return userRepository.findByActivationToken(activationToken) 
        .map(user -> {
          if (user.getActivationExpiredAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("ACTIVATION_TOKEN_EXPIRED");
          }

          user.setIsActive(true);
          user.setActivationToken(null);
          user.setActivationExpiredAt(null);

          UserProfileEntity newProfile = UserProfileEntity.builder()
            .id(user.getId()) 
            .user(user)         
            .build();
          user.setUserProfile(newProfile);
          userRepository.save(user);
          return true;
        })
        .orElse(false);
  }

  public boolean isAccountActive(String email) {
    return userRepository.findByEmailAndIsDeletedFalse(email)
            .map(UserEntity::getIsActive)
            .orElse(false);
  }

  public boolean isAccountDeleted(String email) {
    return userRepository.findByEmailAndIsDeletedFalse(email)
            .map(UserEntity::getIsDeleted)
            .orElse(false);
}

  public UserEntity getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return userRepository.findByEmailAndIsDeletedFalse(email)
                  .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  public UserDTO getPublicUser(String email) {
    UserEntity currentUser;
    if(email == null){
      currentUser = getCurrentUser();
    } else{
      currentUser = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }
    return toDTO(currentUser);
  }

  public Map<String, Object> authenticateAndGenerateToken(UserAuthDTO authDTO){

    String loginInput = (authDTO.getUsername() != null) ? 
    authDTO.getUsername() : authDTO.getEmail();
    
    UserEntity user;
    if (loginInput.contains("@")) {
      user = userRepository.findByEmailAndIsDeletedFalse(loginInput)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }else {
      user = userRepository.findByUsernameAndIsDeletedFalse(loginInput)
          .orElseThrow(() -> new RuntimeException("User not found"));
    }

    if (!user.getIsActive()) {
        throw new RuntimeException("Account is not activated yet.");
    }
    if (Boolean.TRUE.equals(user.getIsDeleted())) { 
        throw new RuntimeException("Account has been deleted.");
    }

    try {
      authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( 
                      loginInput, authDTO.getPassword()
                    ));
      String token = jwtUtil.generateToken(user.getEmail());
      return Map.of(
        "token", token,
        "user", getPublicUser(user.getEmail())
      );
    }catch (AuthenticationException e) {
      throw new RuntimeException("Invalid Email or Password");
    }
  }

  @Transactional
  public void softDeleteUser(String email) {
      UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
              .orElseThrow(() -> new RuntimeException("User not found or already deleted"));

      user.setIsDeleted(true);
      user.setDeletedAt(LocalDateTime.now());
      
      user.setEmail("deleted_" + System.currentTimeMillis() + "_" + user.getEmail());
      user.setUsername("deleted_" + System.currentTimeMillis() + "_" + user.getUsername());
      userRepository.save(user);
  }

  @Transactional
  public UserDTO updateAccount(UserDTO userDTO) {
    // 1. Ambil user yang sedang login saat ini (lebih aman)
    UserEntity user = getCurrentUser();

    // 2. Validasi Username: Cek apakah username baru sudah dipakai orang lain
    if (!user.getUsername().equals(userDTO.getUsername())) {
        if (userRepository.existsByUsernameAndIsDeletedFalse(userDTO.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        user.setUsername(userDTO.getUsername());
    }

    // 3. Update Fullname
    user.setFullname(userDTO.getFullname());
    
    // 4. Update Timestamp
    user.setUpdatedAt(LocalDateTime.now());

    // 5. Simpan dan kembalikan dalam bentuk DTO
    UserEntity updatedUser = userRepository.save(user);
    return toDTO(updatedUser);
  }

public void changePassword(String email, ChangePasswordDTO request) {
    UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 1. CEK PASSWORD LAMA
    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
        // GANTI INI: Jangan pakai BadCredentialsException
        // PAKAI INI: Biar return 400 dan pesannya kebaca di Frontend
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password lama salah!"); 
    }

    // 2. CEK KONFIRMASI
    if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Konfirmasi password tidak cocok!");
    }

    // 3. SIMPAN
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
}

}   
