package com.db.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
        int status = message.contains("not found") ? 404 : (message.contains("Validation failed") ? 400 : 500);
        return ResponseEntity
                .status(HttpStatus.valueOf(status))
                .body(Map.of("error", message, "status", status));
    }
}
