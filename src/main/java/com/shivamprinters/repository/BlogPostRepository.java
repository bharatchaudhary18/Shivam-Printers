package com.shivamprinters.repository;

import com.shivamprinters.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, String> {

    Optional<BlogPost> findBySlug(String slug);

    Page<BlogPost> findByPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    Page<BlogPost> findByTitleContainingIgnoreCaseAndPublishedTrue(String title, Pageable pageable);
}
