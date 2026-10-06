package com.example.calculator_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.calculator_backend.model.CalculationHistory;
import com.example.calculator_backend.service.CalculationHistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@SpringBootApplication
public class CalculatorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorBackendApplication.class, args);
	}

}
