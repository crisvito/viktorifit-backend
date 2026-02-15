package com.viktoria.viktorifit.ml.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMealResponseDTO {
  private String status;
  private TargetDaily target_daily;
  private PlannedTotal planned_total;
  private List<MealItem> meal_plan;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class TargetDaily {
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class PlannedTotal {
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class MealItem {
    private Integer meal_order;
    private String menu_name;
    private Double portion;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
  }
}
