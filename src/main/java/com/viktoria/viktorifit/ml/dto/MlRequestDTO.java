package com.viktoria.viktorifit.ml.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MlRequestDTO {
  private String exercise;
  private String equipment;
  private String diet;
  private String recomendation;
}
