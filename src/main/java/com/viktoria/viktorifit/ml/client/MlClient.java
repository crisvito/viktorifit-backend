package com.viktoria.viktorifit.ml.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MlClient {

  private final RestTemplate restTemplate;
  
  @Value("${ml.api.base-url}")
  private String baseUrl;

  public <T> T post(@NonNull String endpoint, Object request, @NonNull Class<T> responseType) {
    String url = baseUrl + endpoint;
    return restTemplate.postForObject(url, request, responseType);
}
}
