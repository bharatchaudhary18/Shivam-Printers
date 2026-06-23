package com.shivamprinters.service;

import com.shivamprinters.dto.response.ServiceResponse;
import com.shivamprinters.exception.ResourceNotFoundException;
import com.shivamprinters.mapper.EntityMapper;
import com.shivamprinters.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceCatalogService {

    private final ServiceRepository serviceRepository;
    private final EntityMapper entityMapper;

    public List<ServiceResponse> getActiveServices() {
        return serviceRepository.findByActiveTrueOrderBySortOrderAsc().stream()
                .map(entityMapper::toServiceResponse)
                .toList();
    }

    public List<ServiceResponse> getFeaturedServices() {
        return serviceRepository.findByFeaturedTrueAndActiveTrueOrderBySortOrderAsc().stream()
                .map(entityMapper::toServiceResponse)
                .toList();
    }

    public ServiceResponse getBySlug(String slug) {
        return serviceRepository.findBySlug(slug)
                .map(entityMapper::toServiceResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + slug));
    }

    public Page<ServiceResponse> search(String query, Pageable pageable) {
        return serviceRepository.findByNameContainingIgnoreCaseAndActiveTrue(query, pageable)
                .map(entityMapper::toServiceResponse);
    }
}
