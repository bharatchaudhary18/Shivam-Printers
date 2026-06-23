package com.shivamprinters.controller;

import com.shivamprinters.dto.request.RegisterRequest;
import com.shivamprinters.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        model.addAttribute("pageTitle", "Login");
        if (error != null) model.addAttribute("errorMessage", "Invalid email or password");
        if (logout != null) model.addAttribute("successMessage", "You have been logged out");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("pageTitle", "Register");
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Registration Validation Error: " + error.toString());
            });
            model.addAttribute("pageTitle", "Register");
            return "auth/register";
        }
        try {
            userService.register(registerRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("Registration Exception: " + e.getMessage());
            model.addAttribute("pageTitle", "Register");
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "auth/forgot-password";
    }
}
