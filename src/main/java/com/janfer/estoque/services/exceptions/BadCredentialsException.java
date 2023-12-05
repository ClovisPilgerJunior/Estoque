package com.janfer.estoque.services.exceptions;

import java.io.Serial;

public class BadCredentialsException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = 1L;

  public BadCredentialsException(String message) {
    super(message);
  }

  public BadCredentialsException(String message, Throwable cause) {
    super(message, cause);
  }
}
