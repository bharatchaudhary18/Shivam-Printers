package com.shivamprinters.controller;

import com.shivamprinters.service.PdfInvoiceService;
import com.shivamprinters.repository.InvoiceRepository;
import com.shivamprinters.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;
    private final PdfInvoiceService pdfInvoiceService;

    @GetMapping("/dashboard/invoices/{orderId}/download")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {
        var invoice = invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        byte[] pdf = pdfInvoiceService.generateInvoicePdf(invoice);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + invoice.getInvoiceNumber() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
