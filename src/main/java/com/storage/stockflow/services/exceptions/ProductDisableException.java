package com.storage.stockflow.services.exceptions;

import java.io.Serial;

public class ProductDisableException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = 1L;

  public ProductDisableException(String message) {
    super(message);
  }

  public ProductDisableException(String message, Throwable cause) {
    super(message, cause);
  }
}
