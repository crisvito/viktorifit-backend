package com.viktoria.viktorifit.ml.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.ml.entity.WorkoutHistoryEntity;

public interface WorkoutHistoryRepository extends JpaRepository<WorkoutHistoryEntity, Long> {
    List<WorkoutHistoryEntity> findByUserId(Long userId);
    Optional<WorkoutHistoryEntity> findByUserIdAndTitleAndCompletedDate(Long userId, String title, String completedDate);
}