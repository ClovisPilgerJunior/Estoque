package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaCalculatedGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String description;
  private String tipoProduto;
  private String medidaUnidade;
  private String fornecedor;
  private Double entradas;
  private Double saidas;
  private Double perdas;
  private Double saldo;
  private Double valorCompra;
  private Double valorTotal;
  private Long minimo;
  private Long maximo;
  private String resuprimento;
  private boolean ativo;

}
