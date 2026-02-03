package com.viktoria.viktorifit.inquiry.repository;

// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.viktoria.viktorifit.inquiry.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {

}
