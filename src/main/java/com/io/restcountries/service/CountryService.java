package com.io.restcountries.service;

import com.io.restcountries.exception.CountryApiException;
import com.io.restcountries.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Value("${restcountries.api.url}")
    private String restCountriesApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Object getCountryDetails(String countryName) {
        // Make a REST API request to fetch detailed country information

        String url = restCountriesApiUrl + "/name/" + countryName;

        // Make an HTTP GET request to the REST Countries API
        Object[] response = restTemplate.getForObject(url, Object[].class);

        if (response != null && response.length > 0) {
            return response[0];
        } else {
            throw new CountryNotFoundException(countryName);
        }


    }


    public List<Map<String, Object>> getCountries(String filterBy, String sortBy, int page, int pageSize) {
        // Build the URL to get all countries
        String url = restCountriesApiUrl + "/all?fields=name,population,area,languages";

        // Make an HTTP GET request to the REST Countries API
        ResponseEntity<Map[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Map[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Retrieve the full list of countries as a list of maps
            List<Map<String, Object>> allCountries = Arrays.asList(responseEntity.getBody());

            // Apply filtering and sorting
            List<Map<String, Object>> filteredCountries = filterAndSortCountries(allCountries, filterBy, sortBy);

            // Implement pagination
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, filteredCountries.size());
            if (start >= filteredCountries.size() || start < 0 || end < 0) {
                return null; // Return null if the start index is out of range
            }

            return filteredCountries.subList(start, end);
        } else {
            throw new CountryApiException("Failed to retrieve the list of countries");
        }
    }

    private List<Map<String, Object>> filterAndSortCountries(List<Map<String, Object>> countries, String filterBy, String sortBy) {
        // Implement filtering
        if (filterBy != null && !filterBy.isEmpty()) {
            // Split the filter parameters (e.g., "population=1000000" or "language=English")
            String[] filterParams = filterBy.split(",");
            for (String param : filterParams) {
                String[] parts = param.split("=");
                if (parts.length == 2) {
                    String filterField = parts[0];
                    String filterValue = parts[1];
                    countries = applyFilter(countries, filterField, filterValue);
                }
            }
        }

        // Implement sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            // Split the sorting parameter (e.g., "population;asc" or "area;desc")
            String[] sortParams = sortBy.split(";");
            if (sortParams.length == 2) {
                String sortField = sortParams[0];
                String sortOrder = sortParams[1];
                countries = applySort(countries, sortField, sortOrder);
            }
        }

        return countries;
    }

    private List<Map<String, Object>> applyFilter(List<Map<String, Object>> countries, String filterField, String filterValue) {
        // Implement filtering logic


        switch (filterField) {
            case "population":
                int minPopulation = Integer.parseInt(filterValue);
                countries = countries.stream()
                        .filter(country -> {
                            int population = (int) country.get("population");
                            return population >= minPopulation;
                        })
                        .collect(Collectors.toList());
                break;
            case "area":
                double maxArea = Double.parseDouble(filterValue);
                countries = countries.stream()
                        .filter(country -> {
                            double area = (double) country.get("area");
                            return area <= maxArea;
                        })
                        .collect(Collectors.toList());
                break;
            case "language":
                // Implement language filtering based on the desired criteria
                return countries.stream()
                        .filter(country -> {
                            // Check if the "languages" map contains the specified language key
                            Map<String, String> languages = (Map<String, String>) country.get("languages");
                            return languages != null && languages.containsKey(filterValue);
                        })
                        .collect(Collectors.toList());

            // Add more filtering options as needed
        }
        return countries;
    }

    private List<Map<String, Object>> applySort(List<Map<String, Object>> countries, String sortField, String sortOrder) {
        // Implement sorting logic

        countries.sort((c1, c2) -> {
            if (sortOrder.equals("asc")) {
                Comparable value1 = (Comparable) c1.get(sortField);
                Comparable value2 = (Comparable) c2.get(sortField);
                return value1.compareTo(value2);
            } else {
                Comparable value1 = (Comparable) c1.get(sortField);
                Comparable value2 = (Comparable) c2.get(sortField);
                return value2.compareTo(value1);
            }
        });
        return countries;
    }
}

