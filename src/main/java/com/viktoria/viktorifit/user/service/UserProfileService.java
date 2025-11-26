package com.viktoria.viktorifit.user.service;

import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.user.dto.UserProfileDTO;
import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.entity.UserProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

  public UserProfileDTO toDTO(UserProfileEntity userProfileEntity) {
      return UserProfileDTO.builder()
          .id(userProfileEntity.getId())
          .age(userProfileEntity.getAge())
          .gender(userProfileEntity.getGender())
          .height(userProfileEntity.getHeight())
          .weight(userProfileEntity.getWeight())
          .BMI(userProfileEntity.getBMI())
          .diabetes(userProfileEntity.getDiabetes())
          .hypertention(userProfileEntity.getHypertention())
          .createdAt(userProfileEntity.getCreatedAt())
          .updatedAt(userProfileEntity.getUpdatedAt())
          .build();
  }

  public UserProfileEntity toEntity(UserProfileDTO userProfileDTO, UserEntity user) {
    return UserProfileEntity.builder()
        .id(user.getId())
        .user(user)
        .age(userProfileDTO.getAge())
        .gender(userProfileDTO.getGender())
        .height(userProfileDTO.getHeight())
        .weight(userProfileDTO.getWeight())
        .BMI(userProfileDTO.getBMI())
        .diabetes(userProfileDTO.getDiabetes())
        .hypertention(userProfileDTO.getHypertention())
        .build();
}
}
