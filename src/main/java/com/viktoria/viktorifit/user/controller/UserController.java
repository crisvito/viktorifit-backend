package com.viktoria.viktorifit.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viktoria.viktorifit.user.dto.UserAuthDTO;
import com.viktoria.viktorifit.user.dto.UserDTO;
import com.viktoria.viktorifit.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserDTO> registerProfile(@RequestBody UserDTO userDTO) {
    UserDTO registeredProfile = userService.registerProfile(userDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
  }

  @GetMapping("/activate")
  public ResponseEntity<String> activateProfile(@RequestParam String token) {
    boolean isActivated = userService.activateProfile(token);
    if (isActivated) {
      return ResponseEntity.ok("Profile activated successfully.");
    } else { 
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid activation token.");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody UserAuthDTO authDTO){
    try{
      if(!userService.isAccountActive(authDTO.getEmail())){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
          "message", "Account is not active, Please activate your account first." 
        ));
      }

      if(userService.isAccountDeleted(authDTO.getEmail())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
          "message", "Account has been deleted. Please contact support." 
        ));
      }

      Map<String, Object> response = userService.authenticateAndGenerateToken(authDTO);
      return ResponseEntity.ok(response);

    }catch(Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "message", e.getMessage()
      ));
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @DeleteMapping("/users/me")
  public ResponseEntity<String> deleteMyAccount(Authentication authentication) {
      String email = authentication.getName();
      userService.softDeleteUser(email);
      return ResponseEntity.ok("Account deleted successfully");
  }
}
