package com.shivamprinters.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false, length = 150)
    private String clientName;

    @Column(length = 150)
    private String company;

    @Column(length = 150)
    private String designation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(nullable = false)
    @Builder.Default
    private Integer rating = 5;

    @Column(nullable = false)
    @Builder.Default
    private boolean featured = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean published = true;

    @CreationTimestamp
    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;
}
