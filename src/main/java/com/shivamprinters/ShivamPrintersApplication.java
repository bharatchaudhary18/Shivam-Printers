package com.shivamprinters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShivamPrintersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShivamPrintersApplication.class, args);
    }
}
