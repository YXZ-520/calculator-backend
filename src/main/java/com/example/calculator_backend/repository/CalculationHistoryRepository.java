package com.example.calculator_backend.repository;

import com.example.calculator_backend.model.CalculationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalculationHistoryRepository
        extends JpaRepository<CalculationHistory, Long> {

    List<CalculationHistory> findAllByOrderByCreatedAtDesc();
}
