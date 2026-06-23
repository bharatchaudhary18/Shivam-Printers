package com.shivamprinters.controller.api;

import com.shivamprinters.dto.request.OrderRequest;
import com.shivamprinters.dto.request.ProfileUpdateRequest;
import com.shivamprinters.dto.response.InvoiceResponse;
import com.shivamprinters.dto.response.OrderResponse;
import com.shivamprinters.dto.response.UserResponse;
import com.shivamprinters.entity.User;
import com.shivamprinters.service.OrderService;
import com.shivamprinters.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
public class CustomerApiController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserResponse(user.getEmail()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(userService.updateProfile(user.getEmail(), request));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.getCustomerOrders(user.getEmail(), pageable));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestPart("order") OrderRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(orderService.createOrder(user.getEmail(), request, file));
    }

    @GetMapping("/orders/{orderId}/invoice")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getInvoiceByOrderId(orderId));
    }
}
