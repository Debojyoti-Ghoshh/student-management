package com.example.student_management.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "*")
public class CalculatorController {

    @GetMapping
    public Map<String, Object> calculate(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam String operation) {

        double result;

        switch (operation.toLowerCase()) {

            case "add":
                result = a + b;
                break;

            case "subtract":
                result = a - b;
                break;

            case "multiply":
                result = a * b;
                break;

            case "divide":

                if (b == 0) {
                    throw new ArithmeticException(
                            "Cannot divide by zero"
                    );
                }

                result = a / b;
                break;

            case "modulus":

                if (b == 0) {
                    throw new ArithmeticException(
                            "Cannot perform modulus by zero"
                    );
                }

                result = a % b;
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid operation"
                );
        }

        Map<String, Object> response = new HashMap<>();

        response.put("firstNumber", a);
        response.put("secondNumber", b);
        response.put("operation", operation);
        response.put("result", result);

        return response;
    }
}