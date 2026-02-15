package com.viktoria.viktorifit.ml.service;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import com.viktoria.viktorifit.ml.client.MlClient;
import com.viktoria.viktorifit.ml.dto.request.*;
import com.viktoria.viktorifit.ml.dto.response.*;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MlService {

    private final MlClient mlClient;
    public ResponseEntity<?> getUserProgress(UserProgressRequestDTO request) {
        try {
            UserProgressResponseDTO response =
                    mlClient.post("/userprogress", request, UserProgressResponseDTO.class);
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException ex) {
            return buildMlError(ex, "ML validation failed");
        } catch (ResourceAccessException ex) {
            return buildUnavailable(ex);
        } catch (Exception ex) {
            return buildServerError(ex);
        }
    }

    public ResponseEntity<?> getWorkout(UserWorkoutRequestDTO request) {
        try {
            UserWorkoutResponseDTO response =
                    mlClient.post("/workout", request, UserWorkoutResponseDTO.class);
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException ex) {
            return buildMlError(ex, "ML validation failed");
        } catch (ResourceAccessException ex) {
            return buildUnavailable(ex);
        } catch (Exception ex) {
            return buildServerError(ex);
        }
    }

    public ResponseEntity<?> getMeal(UserMealRequestDTO request) {
        try {
            UserMealResponseDTO response =
                    mlClient.post("/meal", request, UserMealResponseDTO.class);
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException ex) {
            return buildMlError(ex, "ML validation failed");
        } catch (ResourceAccessException ex) {
            return buildUnavailable(ex);
        } catch (Exception ex) {
            return buildServerError(ex);
        }
    }

    private ResponseEntity<Map<String, Object>> buildMlError(HttpStatusCodeException ex, String message) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", "error");
        errorBody.put("message", message);
        errorBody.put("ml_error", ex.getResponseBodyAsString());
        return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
    }

    private ResponseEntity<Map<String, Object>> buildUnavailable(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", "error");
        errorBody.put("message", "ML service unreachable");
        errorBody.put("debug_error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorBody);
    }

    private ResponseEntity<Map<String, Object>> buildServerError(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", "error");
        errorBody.put("message", "Unexpected server error");
        errorBody.put("debug_error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }
}
