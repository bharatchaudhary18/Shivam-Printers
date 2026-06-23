package com.shivamprinters.service;

import com.shivamprinters.dto.request.ContactRequest;
import com.shivamprinters.entity.ContactInquiry;
import com.shivamprinters.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactInquiryRepository contactInquiryRepository;
    private final EmailService emailService;

    @Transactional
    public ContactInquiry submit(ContactRequest request) {
        ContactInquiry inquiry = ContactInquiry.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .subject(request.getSubject())
                .message(request.getMessage())
                .service(request.getService())
                .build();
        ContactInquiry saved = contactInquiryRepository.save(inquiry);
        emailService.sendEmail(request.getEmail(), "Thank you for contacting Shivam Printers",
                "Dear " + request.getName() + ",\n\nWe have received your inquiry and will respond shortly.");
        return saved;
    }

    public Page<ContactInquiry> getAll(Pageable pageable) {
        return contactInquiryRepository.findAll(pageable);
    }
}
