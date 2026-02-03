package com.viktoria.viktorifit.faq.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.faq.dto.FaqDTO;
import com.viktoria.viktorifit.faq.entity.FaqEntity;
import com.viktoria.viktorifit.faq.repository.FaqRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqService {

  private final FaqRepository faqRepository;

  private FaqEntity toEntity(FaqDTO faqDTO) {
    return FaqEntity.builder()
            .question(faqDTO.getQuestion())
            .answer(faqDTO.getAnswer())
            .build();
    }

    private FaqDTO toDTO(FaqEntity faqEntity) {
      return FaqDTO.builder()
              .id(faqEntity.getId())
              .question(faqEntity.getQuestion())
              .answer(faqEntity.getAnswer())
              .build();
    }

  public List<FaqDTO> getAllFaqs() {
    return faqRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
  }
  
  public FaqDTO createFaq(FaqDTO faqDTO) {
    FaqEntity entity = toEntity(faqDTO);
    return toDTO(faqRepository.save(entity));
  } 

  public FaqDTO updateFaq(Long id, FaqDTO faqDTO) {
    FaqEntity faq = faqRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("FAQ not found"));
    
    faq.setQuestion(faqDTO.getQuestion());
    faq.setAnswer(faqDTO.getAnswer());
    
    return toDTO(faqRepository.save(faq));
  }

  public void deleteFaq(Long id) {
    if (!faqRepository.existsById(id)) {
        throw new RuntimeException("FAQ not found");
    }
    faqRepository.deleteById(id);
  }

}
