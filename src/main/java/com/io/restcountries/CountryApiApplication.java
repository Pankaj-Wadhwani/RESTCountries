package com.io.restcountries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CountryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryApiApplication.class, args);
    }
}

