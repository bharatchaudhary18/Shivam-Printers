package com.shivamprinters.controller;

import com.shivamprinters.entity.enums.OrderStatus;
import com.shivamprinters.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;
    private final OrderService orderService;
    private final ServiceCatalogService serviceCatalogService;
    private final PortfolioService portfolioService;
    private final ContentService contentService;
    private final ContactService contactService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("stats", dashboardService.getAdminStats());
        return "dashboard/admin/index";
    }

    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) String q,
                        Model model) {
        model.addAttribute("pageTitle", "Manage Users");
        if (q != null && !q.isBlank()) {
            model.addAttribute("users", userService.searchUsers(q, PageRequest.of(page, 15)));
        } else {
            model.addAttribute("users", userService.getAllUsers(PageRequest.of(page, 15)));
        }
        model.addAttribute("searchQuery", q);
        return "dashboard/admin/users";
    }

    @GetMapping("/orders")
    public String orders(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageTitle", "Manage Orders");
        model.addAttribute("orders", orderService.getAllOrders(PageRequest.of(page, 15)));
        model.addAttribute("statuses", OrderStatus.values());
        return "dashboard/admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam OrderStatus status,
                                    RedirectAttributes redirectAttributes) {
        orderService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Order status updated");
        return "redirect:/admin/orders";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("pageTitle", "Manage Services");
        model.addAttribute("services", serviceCatalogService.getActiveServices());
        return "dashboard/admin/services";
    }

    @GetMapping("/portfolio")
    public String portfolio(Model model) {
        model.addAttribute("pageTitle", "Manage Portfolio");
        model.addAttribute("portfolios", portfolioService.getAll(PageRequest.of(0, 20)));
        model.addAttribute("categories", portfolioService.getCategories());
        return "dashboard/admin/portfolio";
    }

    @GetMapping("/testimonials")
    public String testimonials(Model model) {
        model.addAttribute("pageTitle", "Manage Testimonials");
        model.addAttribute("testimonials", contentService.getTestimonials(PageRequest.of(0, 20)));
        return "dashboard/admin/testimonials";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("pageTitle", "Manage Blog");
        model.addAttribute("posts", contentService.getPublishedPosts(PageRequest.of(0, 20)));
        return "dashboard/admin/blog";
    }

    @GetMapping("/inquiries")
    public String inquiries(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageTitle", "Contact Inquiries");
        model.addAttribute("inquiries", contactService.getAll(PageRequest.of(page, 20)));
        return "dashboard/admin/inquiries";
    }
}
