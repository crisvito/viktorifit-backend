package com.viktoria.viktorifit.faq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaqDTO {
  private Long id;
  private String question;
  private String answer;
}
