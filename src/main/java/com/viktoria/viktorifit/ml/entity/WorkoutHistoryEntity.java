package com.viktoria.viktorifit.ml.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Table(name="tbl_workout_histories")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String calories;
    private String totalTime;
    private Integer sets;
    private String reps;
    private String environment;
    private String status; // "FINISHED" atau "PENDING"

    private String completedDate;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}