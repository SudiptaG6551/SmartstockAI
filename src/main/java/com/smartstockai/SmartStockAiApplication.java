package com.smartstockai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartStockAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartStockAiApplication.class, args);
    }
}

