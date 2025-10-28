package com.ojasaar.fairshareapi.config;

import com.ojasaar.fairshareapi.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return createErrorResponseEntity(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException() {
        return createErrorResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
    }

    public ResponseEntity<?> createErrorResponseEntity(HttpStatus httpStatus, String errorMessage) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", errorMessage);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }

}
