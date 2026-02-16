package com.viktoria.viktorifit.user.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.user.dto.UserProfileDTO;
import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.entity.UserProfileEntity;
import com.viktoria.viktorifit.user.repository.UserProfileRepository;
import com.viktoria.viktorifit.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileDTO toDTO(UserProfileEntity entity) {
        return UserProfileDTO.builder()
                .id(entity.getId())
                .dob(entity.getDob())
                .age(entity.getAge())
                .BMI(entity.getBMI())
                .gender(entity.getGender())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .goal(entity.getGoal())
                .level(entity.getLevel())
                .bodyFatCategory(entity.getBodyFatCategory())
                .bodyFatPercentage(entity.getBodyFatPercentage())
                .frequency(entity.getFrequency())
                .duration(entity.getDuration())
                .workoutDays(entity.getWorkoutDays())
                .badminton(entity.getBadminTon())
                .football(entity.getFootball())
                .basketball(entity.getBasketball())
                .volleyball(entity.getVolleyball())
                .swim(entity.getSwim())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

public UserProfileEntity toEntity(UserProfileDTO dto, UserEntity user) {
        UserProfileEntity entity = UserProfileEntity.builder()
                .id(user.getId())
                .user(user)
                .dob(dto.getDob())
                .gender(dto.getGender())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .goal(dto.getGoal())
                .level(dto.getLevel())
                .bodyFatCategory(dto.getBodyFatCategory())
                .bodyFatPercentage(dto.getBodyFatPercentage())
                .frequency(dto.getFrequency())
                .duration(dto.getDuration())
                .workoutDays(dto.getWorkoutDays())
                .badminTon(dto.getBadminton())
                .football(dto.getFootball())
                .basketball(dto.getBasketball())
                .volleyball(dto.getVolleyball())
                .swim(dto.getSwim())
                .build();
        
        // Hitung Age & BMI sebelum dikembalikan
        calculateAutoFields(entity);
        return entity;
    }

    private void calculateAutoFields(UserProfileEntity entity) {
        // 1. Hitung Umur
        if (entity.getDob() != null) {
            int age = Period.between(entity.getDob(), LocalDate.now()).getYears();
            entity.setAge(age);
        }

        // 2. Hitung BMI
        if (entity.getHeight() != null && entity.getWeight() != null && entity.getHeight() > 0) {
            double heightInMeters = entity.getHeight() / 100.0;
            double bmi = entity.getWeight() / (heightInMeters * heightInMeters);
            entity.setBMI(Math.round(bmi * 10.0) / 10.0); // Membulatkan 1 desimal
        }
    }

    public UserProfileDTO saveProfile(String email, UserProfileDTO dto) {
        // 1. Cari User berdasarkan email (dari Token JWT)
        UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Convert DTO ke Entity
        UserProfileEntity entity = toEntity(dto, user);

        // 3. Simpan ke Database
        UserProfileEntity savedEntity = userProfileRepository.save(entity);

        // 4. Balikin jadi DTO
        return toDTO(savedEntity);
    }

    public UserProfileDTO getProfile(String email) {
        UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileEntity profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return toDTO(profile);
    }

    @Transactional
    public UserProfileDTO updateProfile(String email, UserProfileDTO dto) {
        // 1. Cari User
        UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // 2. Cari Profile (pasti ada karena dibuat saat aktivasi, tapi kita amankan pakai orElse)
        UserProfileEntity profile = userProfileRepository.findByUser(user)
                .orElseGet(() -> {
                    UserProfileEntity newEntity = new UserProfileEntity();
                    newEntity.setUser(user);
                    newEntity.setId(user.getId());
                    return newEntity;
                });

        // 3. Update field dari DTO (Data hasil Onboarding)
        profile.setDob(dto.getDob());
        profile.setGender(dto.getGender());
        profile.setHeight(dto.getHeight());
        profile.setWeight(dto.getWeight());
        profile.setGoal(dto.getGoal());
        profile.setLevel(dto.getLevel());
        profile.setBodyFatCategory(dto.getBodyFatCategory());
        profile.setBodyFatPercentage(dto.getBodyFatPercentage());
        profile.setFrequency(dto.getFrequency());
        profile.setDuration(dto.getDuration());
        profile.setBadminTon(dto.getBadminton());
        profile.setFootball(dto.getFootball());
        profile.setBasketball(dto.getBasketball());
        profile.setVolleyball(dto.getVolleyball());
        profile.setSwim(dto.getSwim());
        profile.setWorkoutDays(dto.getWorkoutDays());
        calculateAutoFields(profile);
        // 4. Simpan dan kembalikan sebagai DTO
        UserProfileEntity updatedProfile = userProfileRepository.save(profile);
        return toDTO(updatedProfile);
    }
}