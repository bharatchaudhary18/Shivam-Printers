package com.shivamprinters.repository;

import com.shivamprinters.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findBySlug(String slug);

    List<Portfolio> findByFeaturedTrueAndPublishedTrueOrderByCreatedTimestampDesc();

    Page<Portfolio> findByPublishedTrue(Pageable pageable);

    Page<Portfolio> findByCategoryIdAndPublishedTrue(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Portfolio p WHERE p.published = true AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.client) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Portfolio> searchPublished(@Param("query") String query, Pageable pageable);
}
