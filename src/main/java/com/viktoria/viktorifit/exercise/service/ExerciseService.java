package com.viktoria.viktorifit.exercise.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.exercise.dto.ExerciseDTO;
import com.viktoria.viktorifit.exercise.entity.ExerciseEntity;
import com.viktoria.viktorifit.exercise.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private ExerciseDTO toDTO(ExerciseEntity entity) {
        return ExerciseDTO.builder()
                .id(entity.getExerciseId())
                .name(entity.getName())
                .gifUrl(entity.getGifUrl())
                .targetMuscles(entity.getTargetMuscles())
                .bodyParts(entity.getBodyParts())
                .equipments(entity.getEquipments())
                .secondaryMuscles(entity.getSecondaryMuscles())
                .instructions(entity.getInstructions())
                .environment(entity.getEnvironment())
                .build();
    }

    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(String id) {
        return exerciseRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
    }
}