package com.viktoria.viktorifit.ml.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProgressRequestDTO {
  @JsonProperty("Age")
  private Integer age;

  @JsonProperty("Gender")
  private String gender;

  @JsonProperty("Height_cm")
  private Integer heightCm;

  @JsonProperty("Initial_Weight_kg")
  private Double initialWeightKg;

  @JsonProperty("Goal")
  private String goal;

  @JsonProperty("Level")
  private String level;

  @JsonProperty("Body_Fat_Category")
  private Integer bodyFatCategory;

  @JsonProperty("Body_Fat_Percentage")
  private Double bodyFatPercentage;

  @JsonProperty("Frequency")
  private Integer frequency;

  @JsonProperty("Duration")
  private Integer duration;

  @JsonProperty("Badminton")
  private Integer badminton;

  @JsonProperty("Football")
  private Integer football;

  @JsonProperty("Basketball")
  private Integer basketball;

  @JsonProperty("Volleyball")
  private Integer volleyball;

  @JsonProperty("Swim")
  private Integer swim;
}
