package com.shivamprinters.controller;

import com.shivamprinters.dto.request.OrderRequest;
import com.shivamprinters.dto.request.ProfileUpdateRequest;
import com.shivamprinters.entity.User;
import com.shivamprinters.service.NotificationService;
import com.shivamprinters.service.OrderService;
import com.shivamprinters.service.ServiceCatalogService;
import com.shivamprinters.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class CustomerDashboardController {

    private final UserService userService;
    private final OrderService orderService;
    private final ServiceCatalogService serviceCatalogService;
    private final NotificationService notificationService;

    @GetMapping
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("user", userService.getUserResponse(user.getEmail()));
        model.addAttribute("recentOrders", orderService.getCustomerOrders(user.getEmail(), PageRequest.of(0, 5)));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        return "dashboard/customer/index";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("pageTitle", "Profile");
        model.addAttribute("user", userService.getUserResponse(user.getEmail()));
        model.addAttribute("profileRequest", new ProfileUpdateRequest());
        return "dashboard/customer/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @Valid @ModelAttribute ProfileUpdateRequest profileRequest,
                                RedirectAttributes redirectAttributes) {
        userService.updateProfile(user.getEmail(), profileRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully");
        return "redirect:/dashboard/profile";
    }

    @GetMapping("/orders")
    public String orders(@AuthenticationPrincipal User user,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        model.addAttribute("pageTitle", "My Orders");
        model.addAttribute("orders", orderService.getCustomerOrders(user.getEmail(), PageRequest.of(page, 10)));
        return "dashboard/customer/orders";
    }

    @GetMapping("/orders/new")
    public String newOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("pageTitle", "New Service Request");
        model.addAttribute("services", serviceCatalogService.getActiveServices());
        model.addAttribute("orderRequest", new OrderRequest());
        return "dashboard/customer/new-order";
    }

    @PostMapping("/orders")
    public String createOrder(@AuthenticationPrincipal User user,
                              @Valid @ModelAttribute OrderRequest orderRequest,
                              @RequestParam(required = false) MultipartFile requirementFile,
                              RedirectAttributes redirectAttributes) {
        var order = orderService.createOrder(user.getEmail(), orderRequest, requirementFile);
        redirectAttributes.addFlashAttribute("successMessage", "Order " + order.getOrderNumber() + " placed successfully");
        return "redirect:/dashboard/orders";
    }

    @GetMapping("/invoices")
    public String invoices(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("pageTitle", "Invoices");
        model.addAttribute("orders", orderService.getCustomerOrders(user.getEmail(), PageRequest.of(0, 20)));
        return "dashboard/customer/invoices";
    }

    @GetMapping("/notifications")
    public String notifications(@AuthenticationPrincipal User user,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {
        model.addAttribute("pageTitle", "Notifications");
        model.addAttribute("notifications", notificationService.getUserNotifications(user, PageRequest.of(page, 20)));
        return "dashboard/customer/notifications";
    }
}
