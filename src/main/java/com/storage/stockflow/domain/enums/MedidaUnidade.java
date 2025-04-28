package com.storage.stockflow.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MedidaUnidade {

  UNIDADE(0, "UNIDADE"),

  PACOTE(1, "PACOTE"),

  KILO(2, "KILO"),

  PECA(3, "PEÇA"),

  METRO(4, "METRO"),

  GRAMAS(5, "GRAMAS"),

  CAIXA(6, "CAIXA"),

  SACO(7, "SACO"),

  PAR(8, "PAR"),

  MILHEIRO(9, "MILHEIRO"),

  MILIGRAMAS(10, "MILIGRAMAS");

  private final int cod;
  private final String desc;

  public static MedidaUnidade toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }
    for (MedidaUnidade unidade : MedidaUnidade.values()) {
      if (cod.equals(unidade.getCod())) {
        return unidade;
      }
    }
    throw new IllegalArgumentException("Unidade de medida inválida");
  }

}
