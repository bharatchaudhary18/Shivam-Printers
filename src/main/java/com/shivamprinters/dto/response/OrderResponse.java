package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private String status;
    private BigDecimal totalAmount;
    private String notes;
    private String requirementFileUrl;
    private Instant expectedDeliveryDate;
    private Instant createdTimestamp;
    private List<OrderItemResponse> items;
}
