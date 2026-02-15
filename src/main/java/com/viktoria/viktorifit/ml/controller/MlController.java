package com.viktoria.viktorifit.ml.controller;

import com.viktoria.viktorifit.ml.dto.request.*;
import com.viktoria.viktorifit.ml.service.MlService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ml")
@RequiredArgsConstructor
public class MlController {

  private final MlService mlService;
  
  @PostMapping("/userprogress-recommendation")
  public ResponseEntity<?> getUserProgress(@RequestBody UserProgressRequestDTO request){
    return mlService.getUserProgress(request);
  } 

  @PostMapping("/workout-recommendation")
  public ResponseEntity<?> workout(@RequestBody UserWorkoutRequestDTO request) {
    return mlService.getWorkout(request);
  }

  @PostMapping("/meal-recommendation")
  public ResponseEntity<?> meal(@RequestBody UserMealRequestDTO request) {
    return mlService.getMeal(request);
  }
}
