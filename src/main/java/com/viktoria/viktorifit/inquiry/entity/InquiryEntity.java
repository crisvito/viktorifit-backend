package com.viktoria.viktorifit.inquiry.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_inquiries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InquiryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique=true)
  private String email;
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  private Boolean isResolved;
  
  @CreationTimestamp
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    if(isResolved == null) isResolved = false;
  }
}