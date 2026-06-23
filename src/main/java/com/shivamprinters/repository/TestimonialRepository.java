package com.shivamprinters.repository;

import com.shivamprinters.entity.Testimonial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {

    List<Testimonial> findByFeaturedTrueAndPublishedTrueOrderByCreatedTimestampDesc();

    Page<Testimonial> findByPublishedTrue(Pageable pageable);
}
