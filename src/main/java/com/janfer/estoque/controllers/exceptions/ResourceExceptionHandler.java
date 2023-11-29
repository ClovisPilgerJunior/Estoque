package com.janfer.estoque.controllers.exceptions;

import com.janfer.estoque.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler({ObjectNotFoundException.class})
  public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
    StandardError error = new StandardError(
            System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(),
            "Não encontrado",
            ex.getMessage(),
            request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

    StandardError error = new StandardError(
            System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(),
            "Violation of data integrity",
            ex.getMessage(),
            request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(errors);
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

  @ExceptionHandler({SaldoNegativoException.class})
  public ResponseEntity<StandardError> saldoNegativoException(SaldoNegativoException ex, HttpServletRequest request) {
    StandardError error = new StandardError(
        Instant.now().toEpochMilli(),
        HttpStatus.CONFLICT.value(),
        "A quantidade de saída é maior que o saldo disponível.",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }


}
