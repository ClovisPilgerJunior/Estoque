package com.storage.stockflow.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOrdem {

  AGUARDANDO_LIBERACAO(0, "AGUARDANDO LIBERAÇÃO"),
  LIBERADO(1,"LIBERADO"),
  AGUARDANDO_RECEBIMENTO(2,"AGUARDANDO RECEBIMENTO"),
  RECEBIDO(3,"RECEBIDO"),
  CANCELADO(4, "CANCELADO"),
  EM_ANDAMENTO(5, "EM ANDAMENTO"),
  REVISAR(6, "REVISAR");

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
