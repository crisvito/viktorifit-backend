package com.viktoria.viktorifit.ml.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viktoria.viktorifit.ml.entity.UserProgressEntity;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {
    
    // Fungsi penting untuk mencari roadmap berdasarkan ID User
    Optional<UserProgressEntity> findByUserId(Long userId);
    
}