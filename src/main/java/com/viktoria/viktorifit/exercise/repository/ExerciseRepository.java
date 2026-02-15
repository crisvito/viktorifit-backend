package com.viktoria.viktorifit.exercise.repository;

import com.viktoria.viktorifit.exercise.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, String> {

}