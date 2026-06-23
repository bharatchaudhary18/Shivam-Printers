package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String profileImageUrl;
    private Set<String> roles;
    private Instant createdTimestamp;
}
