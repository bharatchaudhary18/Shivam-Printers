package com.shivamprinters.service;

import com.shivamprinters.dto.response.PortfolioResponse;
import com.shivamprinters.entity.PortfolioCategory;
import com.shivamprinters.exception.ResourceNotFoundException;
import com.shivamprinters.mapper.EntityMapper;
import com.shivamprinters.repository.PortfolioCategoryRepository;
import com.shivamprinters.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioCategoryRepository categoryRepository;
    private final EntityMapper entityMapper;

    public List<PortfolioResponse> getFeatured() {
        return portfolioRepository.findByFeaturedTrueAndPublishedTrueOrderByCreatedTimestampDesc().stream()
                .map(entityMapper::toPortfolioResponse)
                .toList();
    }

    public Page<PortfolioResponse> getAll(Pageable pageable) {
        return portfolioRepository.findByPublishedTrue(pageable)
                .map(entityMapper::toPortfolioResponse);
    }

    public Page<PortfolioResponse> getByCategory(String categorySlug, Pageable pageable) {
        PortfolioCategory category = categoryRepository.findBySlug(categorySlug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return portfolioRepository.findByCategoryIdAndPublishedTrue(category.getId(), pageable)
                .map(entityMapper::toPortfolioResponse);
    }

    public Page<PortfolioResponse> search(String query, Pageable pageable) {
        return portfolioRepository.searchPublished(query, pageable)
                .map(entityMapper::toPortfolioResponse);
    }

    public PortfolioResponse getBySlug(String slug) {
        return portfolioRepository.findBySlug(slug)
                .map(entityMapper::toPortfolioResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found: " + slug));
    }

    public List<PortfolioCategory> getCategories() {
        return categoryRepository.findAll();
    }
}
