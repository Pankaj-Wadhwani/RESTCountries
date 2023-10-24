package com.io.restcountries.controller;


import com.io.restcountries.exception.CountryNotFoundException;
import com.io.restcountries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

//value = "Get detailed information about a country", notes = "Provide the country name to get details.")
//code = 200, message = "Country details retrieved successfully"
//code = 404, message = "Country not found")

    @GetMapping("/details/{countryName}")
    public ResponseEntity<Object> getCountryDetails(@PathVariable String countryName) {
        // Fetch and return detailed country information
        Object country = countryService.getCountryDetails(countryName);
        if (country != null) {
            return ResponseEntity.ok(country);
        } else {
            throw new CountryNotFoundException(countryName);
        }
    }

    //    value = "Get a list of countries", notes = "Filter and sort the list as needed."
//    code = 200, message = "Country list retrieved successfully"
    @GetMapping("/list")
    public List<Map<String, Object>> getCountryList(
            @RequestParam(required = false) String filterBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        // Fetch and return a list of countries based on filters and sorting
        List<Map<String, Object>> countries = countryService.getCountries(filterBy, sortBy, page, pageSize);
        return countries;
    }
}

