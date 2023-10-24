package com.io.restcountries.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${custom.auth.username}")
    private String username;

    @Value("${custom.auth.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

