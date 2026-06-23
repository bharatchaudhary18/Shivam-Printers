package com.shivamprinters.service;

import com.shivamprinters.dto.response.BlogPostResponse;
import com.shivamprinters.dto.response.TestimonialResponse;
import com.shivamprinters.exception.ResourceNotFoundException;
import com.shivamprinters.mapper.EntityMapper;
import com.shivamprinters.repository.BlogPostRepository;
import com.shivamprinters.repository.TestimonialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final TestimonialRepository testimonialRepository;
    private final BlogPostRepository blogPostRepository;
    private final EntityMapper entityMapper;

    public List<TestimonialResponse> getFeaturedTestimonials() {
        return testimonialRepository.findByFeaturedTrueAndPublishedTrueOrderByCreatedTimestampDesc().stream()
                .map(entityMapper::toTestimonialResponse)
                .toList();
    }

    public Page<TestimonialResponse> getTestimonials(Pageable pageable) {
        return testimonialRepository.findByPublishedTrue(pageable)
                .map(entityMapper::toTestimonialResponse);
    }

    public Page<BlogPostResponse> getPublishedPosts(Pageable pageable) {
        return blogPostRepository.findByPublishedTrueOrderByPublishedAtDesc(pageable)
                .map(entityMapper::toBlogPostResponse);
    }

    public BlogPostResponse getPostBySlug(String slug) {
        return blogPostRepository.findBySlug(slug)
                .filter(post -> post.isPublished())
                .map(entityMapper::toBlogPostResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found"));
    }
}
