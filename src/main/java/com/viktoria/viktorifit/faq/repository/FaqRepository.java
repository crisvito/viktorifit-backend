package com.viktoria.viktorifit.faq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viktoria.viktorifit.faq.entity.FaqEntity;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, Long> {

}
