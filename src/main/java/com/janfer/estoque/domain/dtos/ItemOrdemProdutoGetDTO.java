package com.janfer.estoque.domain.dtos;

import lombok.Data;

@Data
public class ItemOrdemProdutoGetDTO {
  private Long produtoCapaId;
  private String produtoCapaDesc;
  private Long quantidade;
  private Long ordemCompraId;
  private Double precoCompra;
  private String observacao;
}

