package com.janfer.estoque.services.exceptions;

public class FornecedorNaoEncontradoException extends RuntimeException {
  public FornecedorNaoEncontradoException(String message){
    super(message);
  }
}
