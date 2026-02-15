package com.viktoria.viktorifit.exercise.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoria.viktorifit.exercise.dto.ExerciseDTO;
import com.viktoria.viktorifit.exercise.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

  private final ExerciseService exerciseService;

  @GetMapping("/exercises")
  public ResponseEntity<List<ExerciseDTO>> getAll() {
    List<ExerciseDTO> exercises = exerciseService.getAllExercises();
    return ResponseEntity.ok(exercises);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExerciseDTO> getById(@PathVariable String id) {
    ExerciseDTO exercise = exerciseService.getExerciseById(id);
    return ResponseEntity.ok(exercise);
  }
}
