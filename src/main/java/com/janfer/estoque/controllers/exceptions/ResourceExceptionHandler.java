package com.janfer.estoque.controllers.exceptions;

import com.janfer.estoque.services.exceptions.ConstraintViolationException;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler({ObjectNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public StandardError objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
    return new StandardError(
        Instant.now().toEpochMilli(),
        HttpStatus.NOT_FOUND.value(),
        "Object Not Found",
        ex.getMessage(),
        request.getRequestURI()
    );
  }
  @ExceptionHandler({DataIntegrityViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public StandardError dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
    return new StandardError(
        Instant.now().toEpochMilli(),
        HttpStatus.BAD_REQUEST.value(),
        "Violation of data integrity",
        ex.getMessage(),
        request.getRequestURI()
    );
  }
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

    ValidationError errors = new ValidationError(
        Instant.now().toEpochMilli(),
        HttpStatus.BAD_REQUEST.value(),
        "Validation error",
        "Validation error in field",
        request.getRequestURI());

    for(FieldError error : ex.getBindingResult().getFieldErrors()){
      errors.addError(error.getField(), error.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
    StandardError error = new StandardError(
        Instant.now().toEpochMilli(),
        HttpStatus.BAD_REQUEST.value(),
        "Invalid Brazilian Individual Taxpayer Registration (CPF) number",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler({ProductDisableException.class})
  public ResponseEntity<StandardError> productDisableException(ProductDisableException ex, HttpServletRequest request) {
    StandardError error = new StandardError(
        Instant.now().toEpochMilli(),
        HttpStatus.CONFLICT.value(),
        "Produto está desativado no sistema",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
