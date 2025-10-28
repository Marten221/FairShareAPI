package com.ojasaar.fairshareapi.config;

import com.ojasaar.fairshareapi.exception.InvalidCredentialsException;
import com.ojasaar.fairshareapi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

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

   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException() {
        return createErrorResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
    }*/

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return createErrorResponseEntity(
                HttpStatus.NOT_FOUND,
                "Endpoint not found: " + e.getRequestURL());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return createErrorResponseEntity(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method " + e.getMethod() + " not allowed for this endpoint");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleMethodNotSupported(NotFoundException e) {
        return createErrorResponseEntity(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    public ResponseEntity<?> createErrorResponseEntity(HttpStatus httpStatus, String errorMessage) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", errorMessage);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }

}
