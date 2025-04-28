package com.storage.stockflow.services.exceptions;

import java.io.Serial;

public class SaldoNegativoException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = 1L;

  public SaldoNegativoException(String message) {
    super(message);
  }

  public SaldoNegativoException(String message, Throwable cause) {
    super(message, cause);
  }
}
