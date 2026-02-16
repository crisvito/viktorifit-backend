package com.viktoria.viktorifit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.entity.UserProfileEntity;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long>{
  Optional<UserProfileEntity> findByUser(UserEntity user);
}
