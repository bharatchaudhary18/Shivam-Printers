package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class TestimonialResponse {

    private Long id;
    private String clientName;
    private String company;
    private String designation;
    private String content;
    private String avatarUrl;
    private Integer rating;
}
