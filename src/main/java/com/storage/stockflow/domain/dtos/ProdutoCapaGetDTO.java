package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private int codSistema;
  private String description;
  private String tipoProduto;
  private String medidaUnidade;
  private String fornecedor;
  private Long minimo;
  private Long maximo;
  private String resuprimento;
  private boolean ativo;

}
