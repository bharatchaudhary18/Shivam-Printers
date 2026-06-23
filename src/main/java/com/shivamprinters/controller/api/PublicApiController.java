package com.shivamprinters.controller.api;

import com.shivamprinters.dto.response.*;
import com.shivamprinters.service.ContentService;
import com.shivamprinters.service.PortfolioService;
import com.shivamprinters.service.ServiceCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicApiController {

    private final ServiceCatalogService serviceCatalogService;
    private final PortfolioService portfolioService;
    private final ContentService contentService;

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponse>> getServices() {
        return ResponseEntity.ok(serviceCatalogService.getActiveServices());
    }

    @GetMapping("/services/{slug}")
    public ResponseEntity<ServiceResponse> getService(@PathVariable String slug) {
        return ResponseEntity.ok(serviceCatalogService.getBySlug(slug));
    }

    @GetMapping("/portfolio")
    public ResponseEntity<Page<PortfolioResponse>> getPortfolio(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q,
            Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(portfolioService.search(q, pageable));
        }
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(portfolioService.getByCategory(category, pageable));
        }
        return ResponseEntity.ok(portfolioService.getAll(pageable));
    }

    @GetMapping("/portfolio/{slug}")
    public ResponseEntity<PortfolioResponse> getPortfolioItem(@PathVariable String slug) {
        return ResponseEntity.ok(portfolioService.getBySlug(slug));
    }

    @GetMapping("/testimonials")
    public ResponseEntity<List<TestimonialResponse>> getTestimonials() {
        return ResponseEntity.ok(contentService.getFeaturedTestimonials());
    }

    @GetMapping("/blog")
    public ResponseEntity<Page<BlogPostResponse>> getBlogPosts(Pageable pageable) {
        return ResponseEntity.ok(contentService.getPublishedPosts(pageable));
    }
}
