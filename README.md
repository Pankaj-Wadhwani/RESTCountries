# RESTCountries
---

# Country Data API Service

This repository contains a backend API service that provides information about countries using the REST Countries API.

## Prerequisites

Before you start, ensure you have the following installed on your system:

- Java Development Kit (JDK)
- Apache Maven
- Git (optional for cloning the repository)

## Installation and Setup

1. Clone the repository (if you haven't already):

   ```bash
   git clone https://github.com/yourusername/country-data-api.git
   cd country-data-api
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

## Usage

### 1. Obtain an Authentication Token

To access the API, you need an authentication token. You can obtain this token by making a POST request to the `/auth` endpoint with the following credentials:

- Username: yourusername
- Password: yourpassword

Example using `curl`:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"yourusername","password":"yourpassword"}' http://localhost:8080/auth/login
```

### 2. Access API Endpoints

Once you have the authentication token, you can access the following API endpoints:

- **GET /country?name=CountryName**
  - Retrieve detailed information about a specific country by providing its name.

curl -H "Authorization:token" http://localhost:8080/country?name=CountryName

- **GET /countries**
  - Retrieve a list of countries based on filters (population, area, language) and sorting (asc, desc). Supports pagination.
 
curl -H "Authorization:token" "http://localhost:8080/countries?filterBy=population=1000000,area=500000&sortBy=population;asc&page=1&pageSize=10"

  Example query parameters:
  - `filterBy=population=1000000,area=500000` (Filter by population and area)
  - `sortBy=population;asc` (Sort by population in ascending order)
  - `page=1&pageSize=10` (Pagination parameters)

## Configuration

You can customize the application configuration by editing the `application.properties` file. For example, you can change the REST Countries API URL, authentication details, and other settings.

---
