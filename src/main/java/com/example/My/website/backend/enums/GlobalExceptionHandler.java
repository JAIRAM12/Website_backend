package com.example.My.website.backend.enums;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle RuntimeException (e.g., duplicate ID or invalid Base64)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        // Return a 400 Bad Request with the exception message
        return ResponseEntity.badRequest().body("❌ " + e.getMessage());
    }

    // Optionally handle any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("❌ Internal server error: " + e.getMessage());
    }
}
