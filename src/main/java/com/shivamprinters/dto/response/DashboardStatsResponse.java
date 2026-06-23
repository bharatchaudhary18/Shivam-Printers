package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DashboardStatsResponse {

    private long totalUsers;
    private long totalOrders;
    private long pendingOrders;
    private long ordersLast30Days;
    private BigDecimal totalRevenue;
    private long newInquiries;
    private long publishedPosts;
}
