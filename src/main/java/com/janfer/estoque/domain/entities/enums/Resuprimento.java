package com.janfer.estoque.domain.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Resuprimento {
  COMPRAR_AGORA(0, "COMPRAR AGORA"),
  QUANTIDADE_IDEAL(1, "QUANTIDADE IDEAL"),
  PRODUTO_EXCESSO(2, "PRODUTO EM EXCESSO");

  private final int cod;
  private final String desc;

  public static Resuprimento toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }
    for (Resuprimento status : Resuprimento.values()) {
      if (cod.equals(status.getCod())) {
        return status;
      }
    }
    throw new IllegalArgumentException("O Status inserido é inválido");
  }
}
