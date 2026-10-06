package com.example.calculator_backend.service;

import com.example.calculator_backend.model.CalculationHistory;
import com.example.calculator_backend.repository.CalculationHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculationHistoryService {

    private final CalculationHistoryRepository repository;

    public CalculationHistoryService(
            CalculationHistoryRepository repository) {

        this.repository = repository;
    }

    public CalculationHistory save(
            String expression,
            double result) {

        CalculationHistory history =
                new CalculationHistory(
                        expression,
                        result,
                        LocalDateTime.now()
                );

        return repository.save(history);
    }

    public List<CalculationHistory> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}