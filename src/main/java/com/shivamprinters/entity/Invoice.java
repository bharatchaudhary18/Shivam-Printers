package com.shivamprinters.entity;

import com.shivamprinters.entity.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private InvoiceStatus status = InvoiceStatus.ISSUED;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "paid_date")
    private Instant paidDate;

    @CreationTimestamp
    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;
}
