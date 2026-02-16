package com.viktoria.viktorifit.user.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoria.viktorifit.user.dto.UserProfileDTO;
import com.viktoria.viktorifit.user.service.UserProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("profile") // Sesuai request frontend
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // 1. Create Profile
    @PostMapping("/create")
    public ResponseEntity<UserProfileDTO> createProfile(@RequestBody UserProfileDTO dto, Principal principal) {
        // Principal.getName() mengambil email/username dari Token JWT yang sedang login
        String email = principal.getName();
        
        UserProfileDTO result = userProfileService.saveProfile(email, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO dto, Principal principal) {
        String email = principal.getName(); 
        UserProfileDTO updated = userProfileService.updateProfile(email, dto);
        return ResponseEntity.ok(updated);
    }

    // 2. Get My Profile (Cek apakah user sudah punya profile)
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(Principal principal) {
        String email = principal.getName();
        
        try {
            UserProfileDTO result = userProfileService.getProfile(email);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Jika profile belum ada, return 404 agar frontend tahu harus redirect ke onboarding
            return ResponseEntity.notFound().build();
        }
    }
}