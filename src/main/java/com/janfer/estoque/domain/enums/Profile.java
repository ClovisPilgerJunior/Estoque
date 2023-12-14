package com.janfer.estoque.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {
  ROLE_ADMIN(0, "ROLE_ADMIN"),
  ROLE_MANAGER(1, "ROLE_MANAGER"),
  ROLE_USER(2, "ROLE_USER"),

  ROLE_FORNECEDOR_VIEW(3, "ROLE_FORNECEDOR"),
  ROLE_FORNECEDOR_CREATE(4, "ROLE_FORNECEDOR_CREATE"),
  ROLE_FORNECEDOR_UPDATE(5, "ROLE_FORNECEDOR_UPDATE"),
  ROLE_FORNECEDOR_DELETE(6, "ROLE_FORNECEDOR_DELETE"),
  ROLE_FORNECEDOR_LIST(7,"ROLE_FORNECEDOR_LIST"),

  ROLE_PRODUTOCAPA_VIEW(8,"ROLE_PRODUTOCAPA_VIEW"),
  ROLE_PRODUTOCAPA_CREATE(9,"ROLE_PRODUTOCAPA_CREATE"),
  ROLE_PRODUTOCAPA_UPDATE(10,"ROLE_PRODUTOCAPA_UPDATE"),
  ROLE_PRODUTOCAPA_DELETE(11,"ROLE_PRODUTOCAPA_DELETE"),
  ROLE_PRODUTOCAPA_LIST(12,"ROLE_PRODUTOCAPA_LIST"),

  // Roles relacionadas a UnidadeProdutiva
  ROLE_UNIDADEPRODUTIVA_VIEW(13,"ROLE_UNIDADEPRODUTIVA_VIEW"),
  ROLE_UNIDADEPRODUTIVA_CREATE(14,"ROLE_UNIDADEPRODUTIVA_CREATE"),
  ROLE_UNIDADEPRODUTIVA_UPDATE(15,"ROLE_UNIDADEPRODUTIVA_UPDATE"),
  ROLE_UNIDADEPRODUTIVA_DELETE(16,"ROLE_UNIDADEPRODUTIVA_DELETE"),
  ROLE_UNIDADEPRODUTIVA_LIST(17,"ROLE_UNIDADEPRODUTIVA_LIST"),

  // Roles relacionadas a ProdutoEntrada
  ROLE_PRODUTOENTRADA_VIEW(18,"ROLE_PRODUTOENTRADA_VIEW"),
  ROLE_PRODUTOENTRADA_CREATE(19,"ROLE_PRODUTOENTRADA_CREATE"),
  ROLE_PRODUTOENTRADA_UPDATE(20,"ROLE_PRODUTOENTRADA_UPDATE"),
  ROLE_PRODUTOENTRADA_DELETE(21,"ROLE_PRODUTOENTRADA_DELETE"),
  ROLE_PRODUTOENTRADA_LIST(22,"ROLE_PRODUTOENTRADA_LIST"),

  // Roles relacionadas a ProdutoSaida
  ROLE_PRODUTOSAIDA_VIEW(23,"ROLE_PRODUTOSAIDA_VIEW"),
  ROLE_PRODUTOSAIDA_CREATE(24,"ROLE_PRODUTOSAIDA_CREATE"),
  ROLE_PRODUTOSAIDA_UPDATE(25,"ROLE_PRODUTOSAIDA_UPDATE"),
  ROLE_PRODUTOSAIDA_DELETE(26,"ROLE_PRODUTOSAIDA_DELETE"),
  ROLE_PRODUTOSAIDA_LIST(27,"ROLE_PRODUTOSAIDA_LIST"),

  // Roles relacionadas a ProdutoPerda
  ROLE_PRODUTOPERDA_VIEW(28,"ROLE_PRODUTOPERDA_VIEW"),
  ROLE_PRODUTOPERDA_CREATE(29,"ROLE_PRODUTOPERDA_CREATE"),
  ROLE_PRODUTOPERDA_UPDATE(30,"ROLE_PRODUTOPERDA_UPDATE"),
  ROLE_PRODUTOPERDA_DELETE(31,"ROLE_PRODUTOPERDA_DELETE"),
  ROLE_PRODUTOPERDA_LIST(32,"ROLE_PRODUTOPERDA_LIST");

  private final Integer code;
  private final String desc;


  public static Profile toEnum(Integer code){
    if (code == null) {
      return null;
    }

    for (Profile profile : Profile.values()) {
      if(code.equals(profile.getCode())){
        return profile;
      }
    }

    throw new IllegalArgumentException("Invalid Profile");
  }

  public Integer getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
