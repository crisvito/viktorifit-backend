package com.viktoria.viktorifit.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByActivationToken(String activaToken);
}
