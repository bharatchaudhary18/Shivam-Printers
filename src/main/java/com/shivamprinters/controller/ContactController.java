package com.shivamprinters.controller;

import com.shivamprinters.dto.request.ContactRequest;
import com.shivamprinters.service.ContactService;
import com.shivamprinters.service.ServiceCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public String submitContact(@Valid @ModelAttribute ContactRequest contactRequest,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill all required fields");
            return "redirect:/contact";
        }
        contactService.submit(contactRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Thank you! We'll get back to you soon.");
        return "redirect:/contact";
    }
}
