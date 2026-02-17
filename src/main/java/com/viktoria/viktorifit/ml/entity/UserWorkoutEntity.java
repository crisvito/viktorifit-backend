package com.viktoria.viktorifit.ml.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;


@Table(name = "tbl_user_workouts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String environment; // "home" atau "gym"

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String workoutData; // JSON String dari workout_plan

    @CreationTimestamp
    private LocalDateTime createdAt;
}