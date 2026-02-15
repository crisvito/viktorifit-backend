package com.viktoria.viktorifit.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

  boolean existsByUsernameAndIsDeletedFalse(String username);
  boolean existsByEmailAndIsDeletedFalse(String email);

  Optional<UserEntity> findByEmailAndIsActiveFalseAndIsDeletedFalse(String email);
  Optional<UserEntity> findByUsernameAndIsDeletedFalse(String username);
  Optional<UserEntity> findByEmailAndIsDeletedFalse(String email);
  Optional<UserEntity> findByActivationToken(String activaToken);

  List<UserEntity> findByIsActiveFalseAndActivationExpiredAtBeforeAndIsDeletedFalse(
      LocalDateTime time
  );
}
