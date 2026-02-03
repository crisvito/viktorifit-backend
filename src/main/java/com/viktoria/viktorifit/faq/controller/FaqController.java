package com.viktoria.viktorifit.faq.controller;

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

import com.viktoria.viktorifit.faq.dto.FaqDTO;
import com.viktoria.viktorifit.faq.service.FaqService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FaqController {

  private final FaqService faqService;

  @GetMapping("/list")
  public ResponseEntity<List<FaqDTO>> getAllFaqs() {
      return ResponseEntity.ok(faqService.getAllFaqs());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/create")
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(faqService.createFaq(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/update/{id}")
    public ResponseEntity<FaqDTO> updateFaq(@PathVariable Long id, @RequestBody FaqDTO request) {
      return ResponseEntity.ok(faqService.updateFaq(id, request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/remove/{id}")
  public ResponseEntity<Map<String, String>> deleteFaq(@PathVariable Long id) {
    try {
        faqService.deleteFaq(id);
        return ResponseEntity.ok(Map.of("message", "FAQ deleted successfully"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "FAQ not found"));
    }
  } 
}
