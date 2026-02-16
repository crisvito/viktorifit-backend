package com.viktoria.viktorifit.ml.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkoutHistoryDTO {
    private Long id;
    private Long userId;
    private String title;
    private String completedDate;
    private String calories;
    private String totalTime;
    private Integer sets;
    private String reps;
    private String environment;
    private String status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}