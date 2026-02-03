package com.viktoria.viktorifit.inquiry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryDTO {
  private Long id;
  private String name;
  private String email;
  private String description;
  private Boolean isResolved;
  private LocalDateTime createdAt;
}
