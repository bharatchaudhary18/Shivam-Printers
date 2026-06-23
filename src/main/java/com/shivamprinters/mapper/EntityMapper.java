package com.shivamprinters.mapper;

import com.shivamprinters.dto.response.*;
import com.shivamprinters.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .city(user.getCity())
                .state(user.getState())
                .profileImageUrl(user.getProfileImageUrl())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()))
                .createdTimestamp(user.getCreatedTimestamp())
                .build();
    }

    public ServiceResponse toServiceResponse(Service service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .slug(service.getSlug())
                .description(service.getDescription())
                .shortDescription(service.getShortDescription())
                .icon(service.getIcon())
                .basePrice(service.getBasePrice())
                .imageUrl(service.getImageUrl())
                .featured(service.isFeatured())
                .createdTimestamp(service.getCreatedTimestamp())
                .build();
    }

    public PortfolioResponse toPortfolioResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .slug(portfolio.getSlug())
                .description(portfolio.getDescription())
                .imageUrl(portfolio.getImageUrl())
                .thumbnailUrl(portfolio.getThumbnailUrl())
                .client(portfolio.getClient())
                .projectDate(portfolio.getProjectDate())
                .featured(portfolio.isFeatured())
                .categoryName(portfolio.getCategory() != null ? portfolio.getCategory().getName() : null)
                .categorySlug(portfolio.getCategory() != null ? portfolio.getCategory().getSlug() : null)
                .createdTimestamp(portfolio.getCreatedTimestamp())
                .build();
    }

    public OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .notes(order.getNotes())
                .requirementFileUrl(order.getRequirementFileUrl())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .createdTimestamp(order.getCreatedTimestamp())
                .items(items)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .serviceName(item.getServiceName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .specifications(item.getSpecifications())
                .build();
    }

    public InvoiceResponse toInvoiceResponse(Invoice invoice) {
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .orderNumber(invoice.getOrder().getOrderNumber())
                .status(invoice.getStatus().name())
                .subtotal(invoice.getSubtotal())
                .taxAmount(invoice.getTaxAmount())
                .totalAmount(invoice.getTotalAmount())
                .pdfUrl(invoice.getPdfUrl())
                .dueDate(invoice.getDueDate())
                .createdTimestamp(invoice.getCreatedTimestamp())
                .build();
    }

    public TestimonialResponse toTestimonialResponse(Testimonial testimonial) {
        return TestimonialResponse.builder()
                .id(testimonial.getId())
                .clientName(testimonial.getClientName())
                .company(testimonial.getCompany())
                .designation(testimonial.getDesignation())
                .content(testimonial.getContent())
                .avatarUrl(testimonial.getAvatarUrl())
                .rating(testimonial.getRating())
                .build();
    }

    public BlogPostResponse toBlogPostResponse(BlogPost post) {
        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .excerpt(post.getExcerpt())
                .content(post.getContent())
                .coverImageUrl(post.getCoverImageUrl())
                .author(post.getAuthor())
                .tags(post.getTags())
                .publishedAt(post.getPublishedAt())
                .build();
    }
}
