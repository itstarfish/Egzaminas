package com.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex The MethodArgumentNotValidException thrown during validation.
     * @return A ResponseEntity containing a map of field validation errors and other metadata.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Extract field-specific validation errors
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Create the response body
        Map<String, Object> response = new HashMap<>();
        response.put("status", "fail");
        response.put("timestamp", LocalDateTime.now());
        response.put("errors", fieldErrors);

        // Return the response entity with status code 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic runtime exceptions.
     *
     * @param ex The RuntimeException encountered during execution.
     * @return A ResponseEntity containing an error message and other metadata.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        // Create the response body
        Map<String, Object> response = new HashMap<>();
        response.put("status", "fail");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", ex.getMessage());

        // Return the response entity with status code 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
