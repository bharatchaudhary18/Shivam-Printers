package com.shivamprinters.util;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public final class OrderNumberGenerator {

    private static final AtomicLong COUNTER = new AtomicLong(System.currentTimeMillis() % 10000);

    private OrderNumberGenerator() {
    }

    public static String generateOrderNumber() {
        return "SP-" + Instant.now().toEpochMilli() + "-" + COUNTER.incrementAndGet();
    }

    public static String generateInvoiceNumber() {
        return "INV-" + Instant.now().toEpochMilli() + "-" + COUNTER.incrementAndGet();
    }
}
