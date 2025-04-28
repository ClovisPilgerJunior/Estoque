package com.storage.stockflow.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOrdemAviamento {

  GERADA(0, "GERADA"),
  EM_SEPARACAO(1, "EM SEPARAÇÃO"),
  ENVIADA(2, "ENVIADA"),
  EM_REVISÃO(3, "EM REVISÃO"),
  REVISADA(4, "REVISADA");

  private final int cod;
  private final String desc;

  public static StatusOrdemAviamento toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }
    for (StatusOrdemAviamento status : StatusOrdemAviamento.values()) {
      if (cod.equals(status.getCod())) {
        return status;
      }
    }
    throw new IllegalArgumentException("O Status inserido é inválido");
  }
}
