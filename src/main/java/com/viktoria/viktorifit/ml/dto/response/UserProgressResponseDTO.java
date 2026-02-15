package com.viktoria.viktorifit.ml.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProgressResponseDTO {
  private String status;
  private Integer total_weeks;
  private List<Roadmap> roadmap;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Roadmap {
      private Integer week;
      private Physical physical;
      private Nutrition nutrition;
      private Macro macro;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Physical {
      private Double weight_kg;
      private Double body_fat_percentage;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Nutrition {
      private Integer calories;
      private Integer water_ml;
      private Double sugar_limit_g;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Macro {
      private Double protein_g;
      private Double carbs_g;
      private Double fat_g;
      private Double fiber_g;
  }
}
