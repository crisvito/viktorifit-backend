package com.viktoria.viktorifit.ml.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MlClient {

    @Qualifier("mlRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${ml.api.base-url}")
    private String baseUrl;

    public <T> T post(String endpoint, Object request, Class<T> responseType) {
        String url = baseUrl + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    responseType
            );
            return response.getBody();

        } catch (HttpStatusCodeException ex) {
            throw new RuntimeException("ML API error: " + ex.getResponseBodyAsString(), ex);
        } catch (ResourceAccessException ex) {
            throw new RuntimeException("ML API unreachable or timeout", ex);
        } catch (RestClientException ex) {
            throw new RuntimeException("Error calling ML API", ex);
        }
    }
}
