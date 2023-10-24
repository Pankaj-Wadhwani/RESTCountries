package com.io.restcountries.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${custom.auth.token}")
    private String expectedToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        if (request.getRequestURI().equals("/auth/login")) {
            // Skip authentication for the login endpoint
            filterChain.doFilter(request, response);
        }
        // Extract the token from the request headers, query parameters, or cookies
        String authToken = extractAuthToken(request);
        if (authToken == null || !authToken.equals(expectedToken)) {
            // Token is missing or invalid, return unauthorized status
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Token is valid; continue processing the request
        filterChain.doFilter(request, response);
    }

    private String extractAuthToken(HttpServletRequest request) {
        // Extract the token from the request (e.g., from headers, query parameters, or cookies)
        String authToken = request.getHeader("Authorization");
        return authToken;
    }
}

