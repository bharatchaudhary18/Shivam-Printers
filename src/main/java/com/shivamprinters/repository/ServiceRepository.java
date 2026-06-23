package com.shivamprinters.repository;

import com.shivamprinters.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findBySlug(String slug);

    List<Service> findByActiveTrueOrderBySortOrderAsc();

    List<Service> findByFeaturedTrueAndActiveTrueOrderBySortOrderAsc();

    Page<Service> findByActiveTrue(Pageable pageable);

    Page<Service> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
}
