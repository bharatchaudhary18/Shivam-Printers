package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PortfolioResponse {

    private Long id;
    private String title;
    private String slug;
    private String description;
    private String imageUrl;
    private String thumbnailUrl;
    private String client;
    private Instant projectDate;
    private boolean featured;
    private String categoryName;
    private String categorySlug;
    private Instant createdTimestamp;
}
