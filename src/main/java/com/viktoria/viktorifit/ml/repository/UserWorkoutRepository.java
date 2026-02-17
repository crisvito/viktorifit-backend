package com.viktoria.viktorifit.ml.repository;

import com.viktoria.viktorifit.ml.entity.UserWorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserWorkoutRepository extends JpaRepository<UserWorkoutEntity, Long> {
    Optional<UserWorkoutEntity> findByUserIdAndEnvironment(Long userId, String environment);
}