package com.viktoria.viktorifit.exercise.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_exercises")
public class ExerciseEntity {

    @Id
    private String exerciseId;

    private String name;

    private String gifUrl;

    @ElementCollection
    @CollectionTable(name = "tbl_exercise_target_muscles", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<String> targetMuscles;

    @ElementCollection
    @CollectionTable(name = "tbl_exercise_body_parts", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<String> bodyParts;

    @ElementCollection
    @CollectionTable(name = "tbl_exercise_equipments", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<String> equipments;

    @ElementCollection
    @CollectionTable(name = "tbl_exercise_secondary_muscles", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<String> secondaryMuscles;

    @ElementCollection
    @CollectionTable(name = "tbl_exercise_instructions", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(columnDefinition = "TEXT")
    private List<String> instructions;

    private String environment;
}