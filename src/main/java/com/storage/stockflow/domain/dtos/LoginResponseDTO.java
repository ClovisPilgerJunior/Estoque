package com.storage.stockflow.domain.dtos;

public record LoginResponseDTO(String token) {
  public String getToken() {
    return token;
  }
}
