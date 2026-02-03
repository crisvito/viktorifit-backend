package com.viktoria.viktorifit.inquiry.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.inquiry.dto.InquiryDTO;
import com.viktoria.viktorifit.inquiry.entity.InquiryEntity;
import com.viktoria.viktorifit.inquiry.repository.InquiryRepository;
import com.viktoria.viktorifit.utility.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryService {

  private final InquiryRepository inquiryRepository;
  private final EmailService emailService;

  public InquiryDTO toDTO(InquiryEntity inquiryEntity) {
    return InquiryDTO.builder()
          .id(inquiryEntity.getId())
          .name(inquiryEntity.getName())
          .email(inquiryEntity.getEmail())
          .description(inquiryEntity.getDescription())
          .createdAt(inquiryEntity.getCreatedAt())
          .isResolved(inquiryEntity.getIsResolved())
          .build();
  }

  public InquiryEntity toEntity(InquiryDTO inquiryDTO) {
    return InquiryEntity.builder()
          .name(inquiryDTO.getName())
          .email(inquiryDTO.getEmail())
          .description(inquiryDTO.getDescription())
          .isResolved(inquiryDTO.getIsResolved())
          .build();
  }

  public InquiryDTO createInquiry(InquiryDTO InquiryDTO) {

    InquiryEntity newInquiry = toEntity(InquiryDTO);
    newInquiry = inquiryRepository.save(newInquiry);

    String subject = "We received your message - Viktorifit Support";
    String body = "Hello " + newInquiry.getName() + ",\n\n" +
                  "Thank you for contacting us." +
                  "We have received your message regarding:\n" +
                  "\"" + newInquiry.getDescription() + "\"\n\n" +
                  "Our team will get back to you within 24 hours.";
    
    try {
        emailService.sendEmail(newInquiry.getEmail(), subject, body);
    } catch (Exception e) {
        System.err.println("Gagal mengirim email auto-reply: " + e.getMessage());
    }

    return toDTO(newInquiry);
  }

  public InquiryDTO markAsResolved(Long id) {
    InquiryEntity inquiry = inquiryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inquiry not found"));

    inquiry.setIsResolved(true);

    InquiryEntity updatedInquiry = inquiryRepository.save(inquiry);

    return toDTO(updatedInquiry);
  }

  public List<InquiryDTO> getAllInquiries() {
    return inquiryRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public InquiryDTO getInquiryById(Long id) {
    InquiryEntity inquiry = inquiryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inquiry not found"));
    return toDTO(inquiry);
  }

  public void deleteInquiry(Long id) {
    if (!inquiryRepository.existsById(id)) {
      throw new RuntimeException("Inquiry not found");
    }
    inquiryRepository.deleteById(id);
  }

}
