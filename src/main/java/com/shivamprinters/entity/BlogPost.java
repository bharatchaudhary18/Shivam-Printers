package com.shivamprinters.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "blog_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost {

    @Id
    @Column(length = 36)
    private String id;

    @PrePersist
    void generateId() {
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
        }
    }

    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false, unique = true, length = 250)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(length = 100)
    private String author;

    @Column(length = 500)
    private String tags;

    @Column(nullable = false)
    @Builder.Default
    private boolean published = false;

    @Column(name = "published_at")
    private Instant publishedAt;

    @CreationTimestamp
    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;
}
