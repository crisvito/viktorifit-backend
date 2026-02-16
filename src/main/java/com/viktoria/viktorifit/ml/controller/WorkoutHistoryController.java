package com.viktoria.viktorifit.ml.controller;

import com.viktoria.viktorifit.ml.dto.WorkoutHistoryDTO;
import com.viktoria.viktorifit.ml.service.WorkoutHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/history")
public class WorkoutHistoryController {

    @Autowired
    private WorkoutHistoryService service;

    @PostMapping
    public WorkoutHistoryDTO saveHistory(@RequestBody WorkoutHistoryDTO dto) {
        return service.saveHistory(dto);
    }

    @GetMapping("/user/{userId}")
    public List<WorkoutHistoryDTO> getUserHistory(@PathVariable Long userId) {
        return service.getUserHistory(userId);
    }

    @PutMapping("/{id}/status")
    public WorkoutHistoryDTO updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}