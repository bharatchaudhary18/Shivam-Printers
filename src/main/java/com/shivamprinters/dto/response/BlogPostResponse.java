package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class BlogPostResponse {

    private String id;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private String coverImageUrl;
    private String author;
    private String tags;
    private Instant publishedAt;
}
