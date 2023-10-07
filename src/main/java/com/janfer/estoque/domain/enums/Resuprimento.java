package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Resuprimento {
  ESTOQUE_NEGATIVO(0, "ESTOQUE NEGATIVO"),
  SALDO_ZERADO(1,"SEM SALDO"),
  COMPRAR_AGORA(2,"COMPRAR AGORA"),
  QUANTIDADE_IDEAL(2, "QUANTIDADE IDEAL"),
  PRODUTO_EXCESSO(3, "PRODUTO EM EXCESSO");

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
