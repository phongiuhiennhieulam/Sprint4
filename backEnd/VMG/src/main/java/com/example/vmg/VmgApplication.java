package com.example.vmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class VmgApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmgApplication.class, args);
    }

}
