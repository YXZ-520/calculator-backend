package com.example.calculator_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_history")
public class CalculationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private double result;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public CalculationHistory() {
    }

    public CalculationHistory(
            String expression,
            double result,
            LocalDateTime createdAt) {

        this.expression = expression;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
