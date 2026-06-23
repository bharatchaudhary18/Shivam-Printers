package com.shivamprinters.service;

import com.shivamprinters.dto.request.OrderRequest;
import com.shivamprinters.dto.response.InvoiceResponse;
import com.shivamprinters.dto.response.OrderResponse;
import com.shivamprinters.entity.*;
import com.shivamprinters.entity.enums.InvoiceStatus;
import com.shivamprinters.entity.enums.OrderStatus;
import com.shivamprinters.exception.ResourceNotFoundException;
import com.shivamprinters.mapper.EntityMapper;
import com.shivamprinters.repository.InvoiceRepository;
import com.shivamprinters.repository.OrderRepository;
import com.shivamprinters.repository.ServiceRepository;
import com.shivamprinters.util.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final InvoiceRepository invoiceRepository;
    private final EntityMapper entityMapper;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final PdfInvoiceService pdfInvoiceService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Transactional
    public OrderResponse createOrder(String customerEmail, OrderRequest request, MultipartFile requirementFile) {
        User customer = userService.findByEmail(customerEmail);
        com.shivamprinters.entity.Service service = null;
        BigDecimal unitPrice = BigDecimal.ZERO;
        String serviceName = request.getServiceName();

        if (request.getServiceId() != null) {
            service = serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
            serviceName = service.getName();
            unitPrice = service.getBasePrice() != null ? service.getBasePrice() : BigDecimal.ZERO;
        }

        int quantity = request.getQuantity() != null ? request.getQuantity() : 1;
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);

        Order order = Order.builder()
                .orderNumber(OrderNumberGenerator.generateOrderNumber())
                .customer(customer)
                .status(OrderStatus.PENDING)
                .totalAmount(totalPrice)
                .notes(request.getNotes())
                .expectedDeliveryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        if (requirementFile != null && !requirementFile.isEmpty()) {
            order.setRequirementFileUrl(fileStorageService.store(requirementFile, "requirements"));
        }

        OrderItem item = OrderItem.builder()
                .service(service)
                .serviceName(serviceName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .specifications(request.getSpecifications())
                .build();
        order.addItem(item);

        Order saved = orderRepository.save(order);
        createInvoice(saved);
        emailService.sendOrderConfirmation(customer.getEmail(), saved.getOrderNumber());
        notificationService.notify(customer, "Order Placed",
                "Your order " + saved.getOrderNumber() + " has been placed successfully.",
                "/dashboard/orders");
        return entityMapper.toOrderResponse(saved);
    }

    private void createInvoice(Order order) {
        BigDecimal tax = order.getTotalAmount().multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
        Invoice invoice = Invoice.builder()
                .invoiceNumber(OrderNumberGenerator.generateInvoiceNumber())
                .order(order)
                .customer(order.getCustomer())
                .status(InvoiceStatus.ISSUED)
                .subtotal(order.getTotalAmount())
                .taxAmount(tax)
                .totalAmount(order.getTotalAmount().add(tax))
                .dueDate(Instant.now().plus(15, ChronoUnit.DAYS))
                .build();
        String pdfUrl = pdfInvoiceService.saveInvoicePdf(invoice);
        invoice.setPdfUrl(pdfUrl);
        invoiceRepository.save(invoice);
    }

    public Page<OrderResponse> getCustomerOrders(String email, Pageable pageable) {
        User customer = userService.findByEmail(email);
        return orderRepository.findByCustomer(customer, pageable).map(entityMapper::toOrderResponse);
    }

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(entityMapper::toOrderResponse);
    }

    public OrderResponse getByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(entityMapper::toOrderResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        notificationService.notify(order.getCustomer(), "Order Updated",
                "Your order " + order.getOrderNumber() + " status is now " + status.name(),
                "/dashboard/orders");
        return entityMapper.toOrderResponse(orderRepository.save(order));
    }

    public InvoiceResponse getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .map(entityMapper::toInvoiceResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }
}
