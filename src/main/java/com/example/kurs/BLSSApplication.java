package com.example.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
@EnableDiscoveryClient
public class BLSSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BLSSApplication.class, args);
    }

}
