# Web Calculator Backend

## Project Overview

This project provides the backend REST API for a web-based calculator.

The backend is developed using Java and Spring Boot. It receives mathematical expressions from the frontend, evaluates them on the server side, returns the result as JSON, and stores successful calculations in a database.

## Backend URL

https://calculator-backend-production-527f.up.railway.app

Backend health check:

https://calculator-backend-production-527f.up.railway.app/api/test

## Technologies

- Java 21
- Spring Boot
- Maven
- Spring Data JPA
- H2 Database
- REST API
- JSON

## Main Features

- Server-side mathematical expression calculation
- Addition, subtraction, multiplication and division
- Operator precedence
- Parentheses
- Decimal values
- Negative values
- Invalid expression handling
- Division-by-zero handling
- Calculation history persistence
- History query
- Delete individual history records

## Project Structure

```text
src/main/java/com/example/calculator_backend/
├── controller/
│   ├── CalculatorController.java
│   └── TestController.java
├── model/
│   └── CalculationHistory.java
├── repository/
│   └── CalculationHistoryRepository.java
├── service/
│   └── CalculationHistoryService.java
└── CalculatorBackendApplication.java