package com.shivamprinters.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.shivamprinters.config.AppProperties;
import com.shivamprinters.entity.Invoice;
import com.shivamprinters.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfInvoiceService {

    private final AppProperties appProperties;

    public byte[] generateInvoicePdf(Invoice invoice) {
        String html = buildInvoiceHtml(invoice);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, appProperties.getBaseUrl());
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception ex) {
            log.error("Failed to generate PDF", ex);
            throw new BusinessException("Failed to generate invoice PDF");
        }
    }

    public String saveInvoicePdf(Invoice invoice) {
        byte[] pdfBytes = generateInvoicePdf(invoice);
        try {
            Path dir = Paths.get(appProperties.getUploadDir(), "invoices");
            Files.createDirectories(dir);
            String fileName = invoice.getInvoiceNumber() + ".pdf";
            Path filePath = dir.resolve(fileName);
            Files.write(filePath, pdfBytes);
            return "/uploads/invoices/" + fileName;
        } catch (IOException ex) {
            throw new BusinessException("Failed to save invoice PDF");
        }
    }

    private String buildInvoiceHtml(Invoice invoice) {
        return """
                <!DOCTYPE html>
                <html><head><meta charset="UTF-8"/><style>
                body{font-family:Arial,sans-serif;padding:40px;color:#111}
                h1{color:#7c3aed}table{width:100%%;border-collapse:collapse;margin-top:20px}
                th,td{border:1px solid #ddd;padding:10px;text-align:left}
                .total{font-size:18px;font-weight:bold;color:#7c3aed}
                </style></head><body>
                <h1>%s</h1>
                <p>Invoice: <strong>%s</strong></p>
                <p>Order: <strong>%s</strong></p>
                <p>Customer: <strong>%s</strong></p>
                <table><tr><th>Description</th><th>Amount</th></tr>
                <tr><td>Subtotal</td><td>₹%s</td></tr>
                <tr><td>Tax</td><td>₹%s</td></tr>
                <tr><td class="total">Total</td><td class="total">₹%s</td></tr>
                </table>
                <p style="margin-top:40px">Thank you for choosing %s!</p>
                </body></html>
                """.formatted(
                appProperties.getName(),
                invoice.getInvoiceNumber(),
                invoice.getOrder().getOrderNumber(),
                invoice.getCustomer().getFullName(),
                invoice.getSubtotal(),
                invoice.getTaxAmount(),
                invoice.getTotalAmount(),
                appProperties.getName()
        );
    }
}
