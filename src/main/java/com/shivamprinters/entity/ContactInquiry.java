package com.shivamprinters.entity;

import com.shivamprinters.entity.enums.InquiryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "contact_inquiries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 150)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(length = 100)
    private String service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private InquiryStatus status = InquiryStatus.NEW;

    @CreationTimestamp
    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;
}
