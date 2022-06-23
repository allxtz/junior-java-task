package com.example.juniorjavatask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
@EnableWebSecurity
@SpringBootApplication
public class JuniorJavaTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuniorJavaTaskApplication.class, args);
    }

}
