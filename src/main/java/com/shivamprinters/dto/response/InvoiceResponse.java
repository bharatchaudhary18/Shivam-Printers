package com.shivamprinters.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private String orderNumber;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String pdfUrl;
    private Instant dueDate;
    private Instant createdTimestamp;
}
