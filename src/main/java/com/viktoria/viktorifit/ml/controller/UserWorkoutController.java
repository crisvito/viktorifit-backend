package com.viktoria.viktorifit.ml.controller;

import com.viktoria.viktorifit.ml.dto.UserWorkoutDTO;
import com.viktoria.viktorifit.ml.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout")
public class UserWorkoutController {

    @Autowired
    private UserWorkoutService service;

    @GetMapping("/{userId}/{env}")
    public ResponseEntity<?> get(@PathVariable Long userId, @PathVariable String env) {
        return service.getWorkout(userId, env)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save/{userId}/{env}")
    public ResponseEntity<?> save(@PathVariable Long userId, @PathVariable String env, @RequestBody UserWorkoutDTO dto) {
        try {
            return ResponseEntity.ok(service.saveWorkout(userId, env, dto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}