package com.viktoria.viktorifit.ml.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserProgressDTO {

    private String status;

    @JsonProperty("total_weeks")
    private int totalWeeks;

    private List<RoadmapItem> roadmap;

    @Data
    public static class RoadmapItem {
        private int week;
        private Physical physical;
        private Nutrition nutrition;
        private Macro macro;
    }

    @Data
    public static class Physical {
        @JsonProperty("weight_kg")
        private double weightKg;
    }

    @Data
    public static class Nutrition {
        private int calories;

        @JsonProperty("water_ml")
        private int waterMl;

        @JsonProperty("sugar_limit_g")
        private double sugarLimitG;
    }

    @Data
    public static class Macro {
        @JsonProperty("protein_g")
        private double proteinG;

        @JsonProperty("carbs_g")
        private double carbsG;

        @JsonProperty("fat_g")
        private double fatG;

        @JsonProperty("fiber_g")
        private double fiberG;
    }
}