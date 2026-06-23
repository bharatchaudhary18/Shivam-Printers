package com.shivamprinters.service;

import com.shivamprinters.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AppProperties appProperties;

    @Async
    public void sendEmail(String to, String subject, String body) {
        if (!appProperties.getMail().isEnabled()) {
            log.info("Email disabled. Would send to {}: {}", to, subject);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(appProperties.getMail().getFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("Email sent to {}", to);
    }

    @Async
    public void sendWelcomeEmail(String to, String name) {
        sendEmail(to, "Welcome to " + appProperties.getName(),
                "Hello " + name + ",\n\nWelcome to " + appProperties.getName() +
                        "! We're excited to help with your printing and design needs.\n\nBest regards,\n" +
                        appProperties.getName() + " Team");
    }

    @Async
    public void sendOrderConfirmation(String to, String orderNumber) {
        sendEmail(to, "Order Confirmation - " + orderNumber,
                "Your order " + orderNumber + " has been received. We'll keep you updated on its progress.");
    }
}
