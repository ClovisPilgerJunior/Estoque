package com.janfer.estoque.domain.dtos;

import lombok.Data;

@Data
public class ItemOrdemProdutoDTO {
  private Long produtoCapaId;
  private Long quantidade;
  private Long ordemCompraId;
  private Double precoCompra;
}

