package com.viktoria.viktorifit.user.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.user.dto.UserAuthDTO;
import com.viktoria.viktorifit.user.dto.UserDTO;
import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.entity.UserProfileEntity;
import com.viktoria.viktorifit.user.repository.UserRepository;
import com.viktoria.viktorifit.utility.email.service.EmailService;
import com.viktoria.viktorifit.utility.JwtUtil;

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

  public UserDTO registerProfile(UserDTO profileDTO) {
    UserEntity newUser = toEntity(profileDTO);
    newUser.setActivationToken(UUID.randomUUID().toString());
    newUser = userRepository.save(newUser);
    
    // Send Activation email
    String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newUser.getActivationToken();
    String subject = "Activate your Viktorifit account";
    String body = "Click on the following link to activate your account: " + activationLink;
    emailService.sendEmail(newUser.getEmail(), subject, body);
    return toDTO(newUser);
  }

  public UserEntity toEntity(UserDTO userDTO) {
    return UserEntity.builder()
        .id(userDTO.getId())
        .fullname(userDTO.getFullname())
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
        .email(userEntity.getEmail())
        .userProfileDTO(userProfileService.toDTO(userEntity.getUserProfile()))
        .createdAt(userEntity.getCreatedAt())
        .updatedAt(userEntity.getUpdatedAt())
        .build();
  }

  public boolean activateProfile(String activationToken) {
    return userRepository.findByActivationToken(activationToken) 
        .map(user -> {
          user.setIsActive(true);
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
    return userRepository.findByEmail(email)
            .map(UserEntity::getIsActive)
            .orElse(false);
  }

  public UserEntity getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return userRepository.findByEmail(email)
                  .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  public UserDTO getPublicUser(String email) {
    UserEntity currentUser;
    if(email == null){
      currentUser = getCurrentUser();
    } else{
      currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }
    return UserDTO.builder()
              .id(currentUser.getId())
              .fullname(currentUser.getFullname())
              .email(currentUser.getEmail())
              .createdAt(currentUser.getCreatedAt())
              .updatedAt(currentUser.getUpdatedAt())
              .build();
  }

  public Map<String, Object> authenticateAndGenerateToken(UserAuthDTO authDTO){
    try {
      authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( 
                      authDTO.getEmail(), authDTO.getPassword()
                    ));
      String token = jwtUtil.generateToken(authDTO.getEmail());
      return Map.of(
        "token", token,
        "user", getPublicUser(authDTO.getEmail())
      );
    }catch (AuthenticationException e) {
      throw new RuntimeException("Invalid Email or Password");
    }
  }
}   
