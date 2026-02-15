package com.viktoria.viktorifit.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.viktoria.viktorifit.user.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullname;

  @Column(nullable=false, unique=true)
  private String email;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;
  
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserProfileEntity userProfile;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;
  
  private Boolean isActive;
  private String activationToken;  
  private LocalDateTime activationExpiredAt;
  
  private Boolean isDeleted;

  @Enumerated(EnumType.STRING)
  private RoleEnum role;
  
  @PrePersist
  public void prePersist() {
    if(isActive == null) isActive = false;
    if (isDeleted == null) isDeleted = false;
  }

}
