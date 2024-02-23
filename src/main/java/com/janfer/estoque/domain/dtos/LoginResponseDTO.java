package com.janfer.estoque.domain.dtos;

public record LoginResponseDTO(String token) {
  public String getToken() {
    return token;
  }
}
