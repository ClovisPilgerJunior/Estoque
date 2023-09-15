package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoProduto {

  MATERIA_PRIMA(0, "MATERIA PRIMA"),
  PRODUTO_PRONTO(1, "PRODUTO PRONTO"),
  DIVERSOS(2, "DIVERSOS");

  private int cod;
  private String desc;

  public static TipoProduto toEnum(Integer cod){
    if(cod == null){
      return null;
    }
    for(TipoProduto tipoProduto : TipoProduto.values()){
      if(cod.equals(tipoProduto.getCod())){
        return tipoProduto;
      }
    }
    throw new IllegalArgumentException("Tipo de produto inválido");
  }


  }
