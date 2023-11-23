package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Setor {

  MARKETING(0, "MERKETING"),
  ESCRITORIO(1, "ESCRITORIO"),
  USO_INTERNO(2, "USO INTERNO"),
  SERVICOS_GERAIS(3, "SERVIÇOS GERAIS"),
  FACCAO(4, "FACÇÃO");


  private int cod;
  private String desc;

  public static Setor toEnum(Integer cod){
    if(cod == null){
      return null;
    }
    for(Setor setor : Setor.values()){
      if(cod.equals(setor.getCod())){
        return setor;
      }
    }
    throw new IllegalArgumentException("Setor inválido");
  }


}
