package com.viktoria.viktorifit.ml.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class UserWorkoutDTO {
    private String status;
    private Map<String, List<ExerciseDTO>> workoutPlan;

    @Data
    public static class ExerciseDTO {
        private String muscleGroup;
        private String exerciseName;
        private int sets;
        private String reps;
        private int caloriesBurned;
        private int durationMinutes;
        private int restMinutes;
        private String equipment;
        private String instructions;
    }
}