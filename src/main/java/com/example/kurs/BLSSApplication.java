package com.example.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BLSSApplication {

    public static void main(String[] args) {
        SpringApplication.run(BLSSApplication.class, args);
    }

}
