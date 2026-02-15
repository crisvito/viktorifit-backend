package com.viktoria.viktorifit.ml.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutRequestDTO {
  @JsonProperty("Age")
    private Integer age;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Height_cm")
    private Integer heightCm;

    @JsonProperty("Weight_kg")
    private Double weightKg;

    @JsonProperty("Body_Fat_Category")
    private Integer bodyFatCategory;

    @JsonProperty("Body_Fat_Percentage")
    private Double bodyFatPercentage;

    @JsonProperty("Goal")
    private String goal;

    @JsonProperty("Frequency")
    private Integer frequency;

    @JsonProperty("Duration")
    private Integer duration;

    @JsonProperty("Level")
    private String level;

    @JsonProperty("Environment")
    private String environment;

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
