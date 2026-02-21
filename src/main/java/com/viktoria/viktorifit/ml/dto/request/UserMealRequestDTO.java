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
public class UserMealRequestDTO {
  @JsonProperty("Daily_Calories")
  private Double dailyCalories;

  @JsonProperty("Target_Protein_g")
  private Double targetProteinG;

  @JsonProperty("Target_Carbs_g")
  private Double targetCarbsG;

  @JsonProperty("Frequency")
  private Integer frequency;
}
