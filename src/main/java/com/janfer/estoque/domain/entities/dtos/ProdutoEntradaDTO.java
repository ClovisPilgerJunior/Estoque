package com.janfer.estoque.domain.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.TipoProduto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class ProdutoEntradaDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private int sku;
  private String desc;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  private Double precoCompra;
  private Long Quantidade;
  private String observacao;
}
