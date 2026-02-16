
package com.viktoria.viktorifit.user.dto;

import java.time.LocalDate;
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
    private LocalDate dob;
    private Integer age;
    private Double BMI;
    private String gender;
    private Double height;
    private Double weight;
    private String goal;
    private String level;
    private Integer bodyFatCategory;
    private Double bodyFatPercentage;
    private Integer frequency;
    private Double duration;
    private String workoutDays;
    private Integer badminton;
    private Integer football;
    private Integer basketball;
    private Integer volleyball;
    private Integer swim;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}