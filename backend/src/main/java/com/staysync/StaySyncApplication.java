package com.staysync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StaySyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaySyncApplication.class, args);
        System.out.println("==================================================");
        System.out.println(" StaySync Hostel Management System Backend Started ");
        System.out.println(" REST API available at: http://localhost:8080/api ");
        System.out.println(" H2 Console available at: http://localhost:8080/h2-console ");
        System.out.println("==================================================");
    }
}
