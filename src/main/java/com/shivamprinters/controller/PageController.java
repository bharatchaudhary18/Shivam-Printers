package com.shivamprinters.controller;

import com.shivamprinters.config.AppProperties;
import com.shivamprinters.service.ContentService;
import com.shivamprinters.service.PortfolioService;
import com.shivamprinters.service.ServiceCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AppProperties appProperties;
    private final ServiceCatalogService serviceCatalogService;
    private final PortfolioService portfolioService;
    private final ContentService contentService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", appProperties.getName());
        model.addAttribute("tagline", appProperties.getTagline());
        model.addAttribute("services", serviceCatalogService.getFeaturedServices());
        model.addAttribute("portfolios", portfolioService.getFeatured());
        model.addAttribute("testimonials", contentService.getFeaturedTestimonials());
        model.addAttribute("pageTitle", "Premium Printing & Graphic Design");
        return "pages/home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About Us");
        return "pages/about";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("pageTitle", "Our Services");
        model.addAttribute("services", serviceCatalogService.getActiveServices());
        return "pages/services";
    }

    @GetMapping("/services/{slug}")
    public String serviceDetail(@PathVariable String slug, Model model) {
        model.addAttribute("pageTitle", "Service Details");
        model.addAttribute("service", serviceCatalogService.getBySlug(slug));
        return "pages/service-detail";
    }

    @GetMapping("/portfolio")
    public String portfolio(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {
        model.addAttribute("pageTitle", "Portfolio");
        model.addAttribute("categories", portfolioService.getCategories());
        model.addAttribute("categories", portfolioService.getCategories());
        model.addAttribute("activeCategory", category);
        model.addAttribute("searchQuery", q);
        var pageable = PageRequest.of(page, 12);
        if (q != null && !q.isBlank()) {
            model.addAttribute("portfolios", portfolioService.search(q, pageable));
        } else if (category != null && !category.isBlank()) {
            model.addAttribute("portfolios", portfolioService.getByCategory(category, pageable));
        } else {
            model.addAttribute("portfolios", portfolioService.getAll(pageable));
        }
        return "pages/portfolio";
    }

    @GetMapping("/portfolio/{slug}")
    public String portfolioDetail(@PathVariable String slug, Model model) {
        model.addAttribute("pageTitle", "Project Details");
        model.addAttribute("portfolio", portfolioService.getBySlug(slug));
        model.addAttribute("related", portfolioService.getFeatured());
        return "pages/portfolio-detail";
    }

    @GetMapping("/pricing")
    public String pricing(Model model) {
        model.addAttribute("pageTitle", "Pricing");
        model.addAttribute("services", serviceCatalogService.getActiveServices());
        return "pages/pricing";
    }

    @GetMapping("/blog")
    public String blog(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("posts", contentService.getPublishedPosts(PageRequest.of(page, 9)));
        return "pages/blog";
    }

    @GetMapping("/blog/{slug}")
    public String blogDetail(@PathVariable String slug, Model model) {
        model.addAttribute("pageTitle", "Blog Post");
        model.addAttribute("post", contentService.getPostBySlug(slug));
        return "pages/blog-detail";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact Us");
        model.addAttribute("services", serviceCatalogService.getActiveServices());
        model.addAttribute("contactRequest", new com.shivamprinters.dto.request.ContactRequest());
        return "pages/contact";
    }
}
