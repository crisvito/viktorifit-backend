package com.viktoria.viktorifit.user.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viktoria.viktorifit.user.dto.ChangePasswordDTO;
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
  public ResponseEntity<?> registerProfile(@RequestBody UserDTO userDTO) {
      try {
          UserDTO registered = userService.registerProfile(userDTO);
          return ResponseEntity.ok(registered);
      } catch (Exception e) {
          e.printStackTrace(); 
          System.out.println("ERROR REGISTER: " + e);
          Map<String, Object> response = new HashMap<>();
          String message = e.getMessage();
          if (message == null) {
              message = "Terjadi kesalahan internal (Null Pointer). Cek Log Railway.";
          }
          response.put("message", message);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
      }
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

  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/update-account")
  public ResponseEntity<UserDTO> updateAccount(@RequestBody UserDTO userDTO) {
      return ResponseEntity.ok(userService.updateAccount(userDTO));
  }

  @PutMapping("/change-password")
  public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordDTO request,
          Principal principal // Otomatis dapet email dari Token JWT
  ) {
      // Panggil service yang tadi kita buat
      userService.changePassword(principal.getName(), request);
      
      return ResponseEntity.ok("Password berhasil diubah!");
  }
}
