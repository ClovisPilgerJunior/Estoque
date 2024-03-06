package com.janfer.estoque.domain.dtos;

import lombok.Data;

@Data
public class ItemOrdemProdutoDTO {
  private Long produtoCapaId;
  private Long numeroNota;
  private Long quantidade;
  private Long ordemCompraId;
  private Double precoCompra;
  private String observacao;
}

