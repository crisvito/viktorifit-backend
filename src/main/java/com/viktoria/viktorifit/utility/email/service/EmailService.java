package com.viktoria.viktorifit.utility.email.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${app.mail.from}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async // Supaya register tidak pending (jalan di background)
    public void sendEmail(String to, String subject, String body) {
        String url = "https://api.brevo.com/v3/smtp/email";

        // Set Header sesuai dokumen API Brevo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        // Susun JSON Body
        Map<String, Object> requestBody = Map.of(
            "sender", Map.of("email", fromEmail, "name", "Viktorifit"),
            "to", List.of(Map.of("email", to)),
            "subject", subject,
            "textContent", body
        );

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            restTemplate.postForEntity(url, entity, String.class);
            System.out.println("DEBUG: Email berhasil dikirim via API ke " + to);
        } catch (Exception e) {
            System.err.println("DEBUG: Gagal kirim email via API: " + e.getMessage());
        }
    }
}