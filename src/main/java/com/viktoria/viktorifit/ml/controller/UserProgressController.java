package com.viktoria.viktorifit.ml.controller;

import com.viktoria.viktorifit.ml.service.UserProgressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.viktoria.viktorifit.ml.dto.UserProgressDTO;
import com.viktoria.viktorifit.ml.entity.UserProgressEntity;

@RestController
@RequestMapping("/progress")
public class UserProgressController {

    @Autowired
    private UserProgressService service;

    // Endpoint untuk ambil data (Dipanggil Angular saat dashboard dibuka)
    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable Long userId) {
        return service.getRecommendation(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint untuk simpan data (Dipanggil Angular setelah dapat hasil dari ML API)
    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@PathVariable Long userId, @RequestBody UserProgressDTO dto) {
        try {
            UserProgressEntity saved = service.saveRecommendation(userId, dto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Gagal menyimpan roadmap: " + e.getMessage());
        }
    }
}