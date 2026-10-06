
package com.example.calculator_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.calculator_backend.service.CalculationHistoryService;

import com.example.calculator_backend.model.CalculationHistory;
import com.example.calculator_backend.service.CalculationHistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500",
    "https://calculator-frontend.2245438578.workers.dev"
})
public class CalculatorController {
    private final CalculationHistoryService historyService;

    public CalculatorController(
            CalculationHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public List<CalculationHistory> getHistory() {
        return historyService.findAll();
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<Void> deleteHistory(
        @PathVariable Long id) {

            historyService.deleteById(id);

            return ResponseEntity.noContent().build();
        }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(
            @RequestBody CalculationRequest request) {

        try {
            if (request.expression() == null ||
                    request.expression().isBlank()) {
                throw new IllegalArgumentException("表达式不能为空");
            }

            double result = new ExpressionParser(
                    request.expression()
            ).parse();

            historyService.save(
                request.expression(),
                result
            );  

            return ResponseEntity.ok(
                    new CalculationResponse(
                            request.expression(),
                            result
                    )
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    public record CalculationRequest(String expression) {
    }

    public record CalculationResponse(
            String expression,
            double result) {
    }

    public record ErrorResponse(String error) {
    }

    // 表达式解析器
    private static class ExpressionParser {

        private final String input;
        private int pos = 0;

        ExpressionParser(String input) {
            this.input = input;
        }

        // 解析完整表达式
        double parse() {
            double result = expression();

            skipWhitespace();

            if (pos != input.length()) {
                throw new IllegalArgumentException(
                        "表达式格式错误，位置：" + pos
                );
            }

            if (!Double.isFinite(result)) {
                throw new IllegalArgumentException(
                        "计算结果超出有效范围"
                );
            }

            return result;
        }

        // 加法和减法
        private double expression() {
            double value = term();

            while (true) {
                skipWhitespace();

                if (match('+')) {
                    value += term();
                } else if (match('-')) {
                    value -= term();
                } else {
                    return value;
                }
            }
        }

        // 乘法和除法
        private double term() {
            double value = factor();

            while (true) {
                skipWhitespace();

                if (match('*')) {
                    value *= factor();

                } else if (match('/')) {
                    double divisor = factor();

                    if (divisor == 0) {
                        throw new IllegalArgumentException(
                                "除数不能为零"
                        );
                    }

                    value /= divisor;

                } else {
                    return value;
                }
            }
        }

        // 数字、括号和正负号
        private double factor() {
            skipWhitespace();

            if (match('+')) {
                return factor();
            }

            if (match('-')) {
                return -factor();
            }

            if (match('(')) {
                double value = expression();

                skipWhitespace();

                if (!match(')')) {
                    throw new IllegalArgumentException(
                            "缺少右括号"
                    );
                }

                return value;
            }

            return number();
        }

        // 解析数字，支持整数和小数
        private double number() {
            skipWhitespace();

            int start = pos;
            boolean hasDigit = false;
            boolean hasDot = false;

            while (pos < input.length()) {
                char c = input.charAt(pos);

                if (Character.isDigit(c)) {
                    hasDigit = true;
                    pos++;

                } else if (c == '.' && !hasDot) {
                    hasDot = true;
                    pos++;

                } else {
                    break;
                }
            }

            if (!hasDigit) {
                throw new IllegalArgumentException(
                        "缺少数字，位置：" + pos
                );
            }

            String text = input.substring(start, pos);
            double value = Double.parseDouble(text);

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(
                        "数字超出有效范围"
                );
            }

            return value;
        }

        private boolean match(char expected) {
            if (pos < input.length()
                    && input.charAt(pos) == expected) {
                pos++;
                return true;
            }

            return false;
        }

        private void skipWhitespace() {
            while (pos < input.length()
                    && Character.isWhitespace(input.charAt(pos))) {
                pos++;
            }
        }
    }
}