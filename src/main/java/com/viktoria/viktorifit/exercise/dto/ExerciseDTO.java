package com.viktoria.viktorifit.exercise.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseDTO {
  private String id;
  private String name;
  private String gifUrl;
  private List<String> targetMuscles;
  private List<String> bodyParts;
  private List<String> equipments;
  private List<String> secondaryMuscles;
  private List<String> instructions;
  private String environment;
}
