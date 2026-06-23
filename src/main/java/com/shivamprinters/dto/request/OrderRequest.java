package com.shivamprinters.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    @NotBlank(message = "Service is required")
    private String serviceName;

    private Long serviceId;
    private Integer quantity;
    private String specifications;
    private String notes;
}
