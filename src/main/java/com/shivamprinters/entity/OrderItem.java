package com.shivamprinters.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(nullable = false, length = 200)
    private String serviceName;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "unit_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @CreationTimestamp
    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Instant createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_timestamp", nullable = false)
    private Instant updatedTimestamp;
}
