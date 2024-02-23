package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOrdem {

  CRIADA(0, "CRIADA"),
  FATURADA(1,"FATURADA"),
  NAO_FATURADA(2,"NÃO FATURADA"),
  CANCELADO(3,"CANCELADO"),
  AGUARDANDO_LIBERACAO(4, "AGUARDANDO LIBERAÇÃO"),
  EM_ANDAMENTO(5, "EM ANDAMENTO"),
  PENDENTE(6, "PENDENTE");

  private final int cod;
  private final String desc;

  public static StatusOrdem toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }
    for (StatusOrdem status : StatusOrdem.values()) {
      if (cod.equals(status.getCod())) {
        return status;
      }
    }
    throw new IllegalArgumentException("O Status inserido é inválido");
  }
}
