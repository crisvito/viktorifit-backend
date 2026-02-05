package com.viktoria.viktorifit.inquiry.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoria.viktorifit.inquiry.dto.InquiryDTO;
import com.viktoria.viktorifit.inquiry.service.InquiryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping("/create") 
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO request) {
        InquiryDTO result = inquiryService.createInquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<InquiryDTO>> getAllInquiries() {
        List<InquiryDTO> result = inquiryService.getAllInquiries();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<InquiryDTO> getInquiryById(@PathVariable Long id) {
        InquiryDTO result = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long id) {
        try {
            inquiryService.deleteInquiry(id);
            return ResponseEntity.ok("Inquiry deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inquiry not found.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/resolve/{id}")
    public ResponseEntity<Map<String, Object>> markAsResolved(@PathVariable Long id) {
        try {
            InquiryDTO result = inquiryService.markAsResolved(id);
            return ResponseEntity.ok(Map.of(
                "message", "Inquiry marked as resolved.",
                "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "message", e.getMessage()
            ));
        }
    }
}
