package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class ServiceResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private String icon;
    private BigDecimal basePrice;
    private String imageUrl;
    private boolean featured;
    private Instant createdTimestamp;
}
