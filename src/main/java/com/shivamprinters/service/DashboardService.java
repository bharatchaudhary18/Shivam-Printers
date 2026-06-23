package com.shivamprinters.service;

import com.shivamprinters.dto.response.DashboardStatsResponse;
import com.shivamprinters.entity.User;
import com.shivamprinters.entity.enums.InquiryStatus;
import com.shivamprinters.entity.enums.OrderStatus;
import com.shivamprinters.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ContactInquiryRepository contactInquiryRepository;
    private final BlogPostRepository blogPostRepository;

    public DashboardStatsResponse getAdminStats() {
        return DashboardStatsResponse.builder()
                .totalUsers(userRepository.count())
                .totalOrders(orderRepository.count())
                .pendingOrders(orderRepository.countByStatus(OrderStatus.PENDING))
                .ordersLast30Days(orderRepository.countOrdersSince(Instant.now().minus(30, ChronoUnit.DAYS)))
                .totalRevenue(orderRepository.calculateTotalRevenue())
                .newInquiries(contactInquiryRepository.countByStatus(InquiryStatus.NEW))
                .publishedPosts(blogPostRepository.findByPublishedTrueOrderByPublishedAtDesc(
                        org.springframework.data.domain.Pageable.unpaged()).getTotalElements())
                .build();
    }
}
