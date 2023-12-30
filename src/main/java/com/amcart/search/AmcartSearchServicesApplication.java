package com.amcart.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.amcart.search")
public class AmcartSearchServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmcartSearchServicesApplication.class, args);
    }

}
