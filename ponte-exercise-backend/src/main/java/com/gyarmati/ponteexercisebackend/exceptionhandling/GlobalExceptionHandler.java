package com.gyarmati.ponteexercisebackend.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        validationErrors.forEach(validationError -> {
            log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        });
        return new ResponseEntity<>(validationErrors, BAD_REQUEST);
    }

    @ExceptionHandler(BothEmailAndPhoneNumberCantBeEmptyException.class)
    public ResponseEntity<List<ValidationError>> bothEmailAndPhoneNumberCantBeEmptyExceptionException(BothEmailAndPhoneNumberCantBeEmptyException exception) {
        ValidationError validationError = new ValidationError("message",
                exception.getMessage());
        log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        return new ResponseEntity<>(List.of(validationError), BAD_REQUEST);
    }
}
