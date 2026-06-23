package com.shivamprinters.controller.api;

import com.shivamprinters.dto.response.DashboardStatsResponse;
import com.shivamprinters.dto.response.OrderResponse;
import com.shivamprinters.dto.response.UserResponse;
import com.shivamprinters.entity.enums.OrderStatus;
import com.shivamprinters.service.DashboardService;
import com.shivamprinters.service.OrderService;
import com.shivamprinters.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final DashboardService dashboardService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        return ResponseEntity.ok(dashboardService.getAdminStats());
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getUsers(
            @RequestParam(required = false) String q,
            Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(userService.searchUsers(q, pageable));
        }
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}
