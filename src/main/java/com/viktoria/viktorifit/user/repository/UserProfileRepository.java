package com.viktoria.viktorifit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.user.entity.UserProfileEntity;


public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long>{

}
