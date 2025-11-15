package com.example.builder.landingbuilder.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PythonServiceException.class)
    public ResponseEntity<Map<String, Object>> handlePythonServiceException(PythonServiceException ex) {
        Map<String, Object> body = Map.of(
                "error", "python_service_error",
                "service", ex.getService(),
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = Map.of(
                "error", "internal_error",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
