package com.viktoria.viktorifit.ml.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoria.viktorifit.ml.dto.UserProgressDTO;
import com.viktoria.viktorifit.ml.entity.UserProgressEntity;
import com.viktoria.viktorifit.ml.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserProgressService {

    @Autowired
    private UserProgressRepository repository;

    @Autowired
    private ObjectMapper objectMapper; // Alat untuk tukar Object ke JSON String

    // 1. FUNGSI SAVE (Simpan atau Update)
    public UserProgressEntity saveRecommendation(Long userId, UserProgressDTO dto) throws JsonProcessingException {
        // Cari jika user sudah ada program atau belum
        Optional<UserProgressEntity> existing = repository.findByUserId(userId);
        
        UserProgressEntity progress = existing.orElse(new UserProgressEntity());
        
        progress.setUserId(userId);
        // Tukar DTO roadmap menjadi String JSON untuk disimpan di database
        progress.setRoadmapData(objectMapper.writeValueAsString(dto));
        
        // Jika program baru, set tarikh mula hari ini
        if (progress.getStartDate() == null) {
            progress.setStartDate(LocalDate.now());
        }
        
        return repository.save(progress);
    }

    // 2. FUNGSI GET
    public Optional<UserProgressEntity> getRecommendation(Long userId) {
        return repository.findByUserId(userId);
    }
}