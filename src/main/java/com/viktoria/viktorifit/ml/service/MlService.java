package com.viktoria.viktorifit.ml.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viktoria.viktorifit.ml.dto.MlRequestDTO;
import com.viktoria.viktorifit.ml.dto.MlResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MlService {

  private final RestTemplate restTemplate;
  private static final String ML_URL = "http://localhost:8000/predict";

  public MlResponseDTO predict(MlRequestDTO request) {
    return restTemplate.postForObject(
      ML_URL,
      request,
      MlResponseDTO.class
    );
  }
}
