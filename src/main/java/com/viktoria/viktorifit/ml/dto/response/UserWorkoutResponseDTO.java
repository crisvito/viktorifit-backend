package com.viktoria.viktorifit.ml.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserWorkoutResponseDTO {
    private String status;
    private Map<String, List<Exercise>> workout_plan;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Exercise {
        private String muscle_group;
        private String exercise_name;
        private Integer sets;
        private String reps;
        private Integer calories_burned;
        private Integer duration_minutes;
        private Integer rest_minutes;
        private String equipment;
        private String instructions;
    }
}
