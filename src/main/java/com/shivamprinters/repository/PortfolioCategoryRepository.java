package com.shivamprinters.repository;

import com.shivamprinters.entity.PortfolioCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioCategoryRepository extends JpaRepository<PortfolioCategory, Long> {

    Optional<PortfolioCategory> findBySlug(String slug);
}
