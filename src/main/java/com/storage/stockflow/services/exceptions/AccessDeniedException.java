package com.storage.stockflow.services.exceptions;

import java.io.Serial;

public class AccessDeniedException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = 1L;

  public AccessDeniedException(String message) {
    super(message);
  }

  public AccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }
}
