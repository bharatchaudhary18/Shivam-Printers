package com.shivamprinters.repository;

import com.shivamprinters.entity.Invoice;
import com.shivamprinters.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    Optional<Invoice> findByOrderId(Long orderId);

    Page<Invoice> findByCustomer(User customer, Pageable pageable);
}
