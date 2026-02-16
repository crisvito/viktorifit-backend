package com.viktoria.viktorifit.ml.service;

import com.viktoria.viktorifit.ml.dto.WorkoutHistoryDTO;
import com.viktoria.viktorifit.ml.repository.WorkoutHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.viktoria.viktorifit.ml.entity.WorkoutHistoryEntity;

@Service
public class WorkoutHistoryService {

    @Autowired
    private WorkoutHistoryRepository repository;

    public WorkoutHistoryDTO saveHistory(WorkoutHistoryDTO dto) {
        String today = java.time.LocalDate.now().toString();

        Optional<WorkoutHistoryEntity> existing = repository.findByUserIdAndTitleAndCompletedDate(
            dto.getUserId(), dto.getTitle(), today
        );

        WorkoutHistoryEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
        } else {
            entity = new WorkoutHistoryEntity();
            entity.setUserId(dto.getUserId());
            entity.setTitle(dto.getTitle());
            entity.setCompletedDate(today);
        }

        // Ubah bagian ini supaya fleksibel
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus()); 
        } else {
            entity.setStatus("PENDING");
        }
        
        entity.setCalories(dto.getCalories());
        entity.setTotalTime(dto.getTotalTime());
        entity.setSets(dto.getSets());
        entity.setReps(dto.getReps());
        entity.setEnvironment(dto.getEnvironment());

        return toDTO(repository.save(entity));
    }

    public List<WorkoutHistoryDTO> getUserHistory(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public WorkoutHistoryDTO updateStatus(Long id, String status) {
        WorkoutHistoryEntity entity = repository.findById(id).orElseThrow();
        entity.setStatus(status);
        return toDTO(repository.save(entity));
    }

    @SuppressWarnings("unused")
    private WorkoutHistoryEntity toEntity(WorkoutHistoryDTO dto) {
        WorkoutHistoryEntity entity = new WorkoutHistoryEntity();
        entity.setUserId(dto.getUserId());
        entity.setTitle(dto.getTitle());
        entity.setCalories(dto.getCalories());
        entity.setTotalTime(dto.getTotalTime());
        entity.setSets(dto.getSets());
        entity.setReps(dto.getReps());
        entity.setEnvironment(dto.getEnvironment());
        return entity;
    }

    private WorkoutHistoryDTO toDTO(WorkoutHistoryEntity entity) {
        WorkoutHistoryDTO dto = new WorkoutHistoryDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTitle(entity.getTitle());
        dto.setCalories(entity.getCalories());
        dto.setTotalTime(entity.getTotalTime());
        dto.setSets(entity.getSets());
        dto.setReps(entity.getReps());
        dto.setEnvironment(entity.getEnvironment());
        dto.setStatus(entity.getStatus());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}