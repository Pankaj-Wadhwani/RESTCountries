package com.io.restcountries.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String countryName) {
        super("Country not found: " + countryName);
    }
}

