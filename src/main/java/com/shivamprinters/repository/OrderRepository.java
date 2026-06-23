package com.shivamprinters.repository;

import com.shivamprinters.entity.Order;
import com.shivamprinters.entity.User;
import com.shivamprinters.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByCustomer(User customer, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    List<Order> findTop5ByCustomerOrderByCreatedTimestampDesc(User customer);

    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED'")
    BigDecimal calculateTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdTimestamp >= :since")
    long countOrdersSince(@Param("since") java.time.Instant since);
}
