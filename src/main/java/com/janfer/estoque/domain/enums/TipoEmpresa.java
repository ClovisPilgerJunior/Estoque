package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoEmpresa {
  FORNECEDOR(0, "FORNECEDOR"),
  CLIENTE(1, "CLIENTE");

  private int cod;
  private String desc;

  public static TipoEmpresa toEnum(Integer cod){
    if(cod == null) {
      return null;
    }
    for(TipoEmpresa tipoEmpresa : TipoEmpresa.values()){
      if(cod.equals(tipoEmpresa.getCod())){
        return tipoEmpresa;
      }
    }
    throw new IllegalArgumentException("Tipo de empresa inválido");
  }

}
