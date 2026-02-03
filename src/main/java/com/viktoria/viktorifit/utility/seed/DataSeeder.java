package com.viktoria.viktorifit.utility.seed;

import java.time.LocalDateTime;

import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.enums.RoleEnum;
import com.viktoria.viktorifit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@viktorifit.com";
        String adminUsername = "Admin";
        
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setEmail(adminEmail);
            admin.setFullname("Admin boss");
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(RoleEnum.ADMIN);
            admin.setIsActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(admin);
            System.out.println("Akun Admin Berhasil Dibuat: " + adminEmail);
        } else {
            System.out.println("ℹAkun Admin Sudah Ada.");
        }
    }
}
