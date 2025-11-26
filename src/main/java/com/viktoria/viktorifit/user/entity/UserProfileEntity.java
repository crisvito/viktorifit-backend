package com.viktoria.viktorifit.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_user_profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProfileEntity {
  @Id
  @Column(name = "user_id")
  private Long id;

  private Integer age;
  private String gender;
  private Integer height;
  private Integer weight;
  private Integer BMI;
  private Boolean diabetes;
  private Boolean hypertention;

  @OneToOne
  @JoinColumn(name = "user_id", nullable=false, unique=true)
  private UserEntity user;

  @Column(updatable=false)
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime UpdatedAt;
}
