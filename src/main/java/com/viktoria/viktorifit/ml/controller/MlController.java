package com.viktoria.viktorifit.ml.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.viktoria.viktorifit.ml.dto.MlRequestDTO;
import com.viktoria.viktorifit.ml.dto.MlResponseDTO;
import com.viktoria.viktorifit.ml.service.MlService;


@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
public class MlController {

  private final MlService mlServce;

  @PostMapping("/predict")
  public MlResponseDTO predict(@RequestBody MlRequestDTO request) {      
      return mlServce.predict(request);
  }
  
}
