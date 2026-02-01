package com.viktoria.viktorifit.ml.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MlResponseDTO {
  private Integer age;
  private Double height;
  private Double weight;
  private String sex;
  private String hypertension;
  private String diabetes;
  private String fitnessGoal;
}
