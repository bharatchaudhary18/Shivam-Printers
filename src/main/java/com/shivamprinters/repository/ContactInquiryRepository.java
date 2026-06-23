package com.shivamprinters.repository;

import com.shivamprinters.entity.ContactInquiry;
import com.shivamprinters.entity.enums.InquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {

    Page<ContactInquiry> findByStatus(InquiryStatus status, Pageable pageable);

    long countByStatus(InquiryStatus status);
}
