package com.viktoria.viktorifit.ml.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoria.viktorifit.ml.entity.UserWorkoutEntity;
import com.viktoria.viktorifit.ml.dto.UserWorkoutDTO;
import com.viktoria.viktorifit.ml.repository.UserWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWorkoutService {

    @Autowired
    private UserWorkoutRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public UserWorkoutEntity saveWorkout(Long userId, String env, UserWorkoutDTO dto) throws Exception {
        Optional<UserWorkoutEntity> existing = repository.findByUserIdAndEnvironment(userId, env);
        UserWorkoutEntity workout = existing.orElse(new UserWorkoutEntity());
        
        workout.setUserId(userId);
        workout.setEnvironment(env.toLowerCase());
        workout.setWorkoutData(objectMapper.writeValueAsString(dto));
        
        System.out.println(">>> [SUCCESS] Workout " + env + " disimpan untuk User: " + userId);
        return repository.save(workout);
    }

    public Optional<UserWorkoutEntity> getWorkout(Long userId, String env) {
        return repository.findByUserIdAndEnvironment(userId, env.toLowerCase());
    }
}