package com.viktoria.viktorifit.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProfileDTO {
  private Long id;
  private Integer age;
  private String gender;
  private Integer height;
  private Integer weight;
  private Integer BMI;
  private Boolean diabetes;
  private Boolean hypertention;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
