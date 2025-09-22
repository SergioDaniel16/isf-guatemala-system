package com.isf.guatemala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class, 
    ManagementWebSecurityAutoConfiguration.class
})
@EntityScan(basePackages = "com.isf.guatemala.entity")
@EnableJpaRepositories(basePackages = "com.isf.guatemala.repository")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}