package com.example.springexception.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Not Found");
        pd.setType(URI.create("http://localhost:8080/errors/not-found"));
        pd.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequest(BadRequestException ex) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Bad Request");
//        pd.setDetail("Validation failed");
        pd.setProperty("timestamp", Instant.now());

        pd.setProperty("errors", ex.getErrors());

        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ProblemDetail> handleConflict(ConflictException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setTitle("Conflict");
        pd.setType(URI.create("http://localhost:8080/errors/duplicate-user"));
        pd.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ProblemDetail> handleHandlerMethodValidation(
            HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getParameterValidationResults().forEach(result -> {
            String field = result.getMethodParameter().getParameterName();

            result.getResolvableErrors().forEach(error -> {
                errors.put(field, error.getDefaultMessage());
            });
        });

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Bad Request");
        pd.setDetail("Validation failed");
        pd.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setTitle("Validation Error");
        pd.setProperty("errors", errors);
        pd.setType(URI.create("http://localhost:8080/errors/validation"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> {
            String field = v.getPropertyPath().toString();
            errors.put(field, v.getMessage());
        });
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setTitle("Validation Error");
        pd.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(java.sql.SQLException.class)
    public ResponseEntity<ProblemDetail> handleSQLException(java.sql.SQLException ex) {
        String message;
        if (ex.getMessage() != null && (ex.getMessage().contains("foreign key") ||
                ex.getMessage().contains("update or delete"))) {
            message = "Cannot delete/update this resource. It is still referenced by other records (events/attendees).";
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, message);
            pd.setTitle("Operation Not Allowed");
            pd.setType(URI.create("http://localhost:8080/errors/conflict"));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
        }
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Database Error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleInvalidJson(HttpMessageNotReadableException ex) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Bad Request");
        pd.setDetail("Invalid JSON in request body. Please check syntax.");
        pd.setProperty("timestamp", Instant.now());
        pd.setType(URI.create("http://localhost:8080/errors/malformed-json"));

        return ResponseEntity.badRequest().body(pd);
    }

    // Catch all other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ProblemDetail> handleGeneralException(Exception ex) {
//        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred");
//        pd.setTitle("Internal Server Error");
//        pd.setType(URI.create("http://localhost:8080/errors/internal-server-error"));
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
//    }
}
