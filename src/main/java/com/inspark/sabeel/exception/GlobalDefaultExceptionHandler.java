package com.inspark.sabeel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {


    //     Exception handler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Exception handler for NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException ex,
            final WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ExceptionResponse(ex.getTitleKey(), ex.getMessageKey(), ex), HttpStatus.NOT_FOUND);
    }

    // Exception handler for DisabledUserException
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleDisabledUserException(
            DisabledException ex,
            final WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ExceptionResponse("error.user.disabled", "error.user.disabled", ex), HttpStatus.FORBIDDEN);
    }

    // Exception handler for BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex,
            final WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ExceptionResponse("error.bad.credentials", "error.bad.credentials", ex), HttpStatus.UNAUTHORIZED);
    }

    // Exception handler for ConflictException
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(
            ConflictException ex,
            final WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ExceptionResponse(ex.getTitleKey(), ex.getMessageKey(), ex), HttpStatus.CONFLICT);
    }
}