package com.janfer.estoque.domain.dtos;

import lombok.Data;

@Data
public class ItemOrdemProdutoDTO {
  private Long produtoCapaId;
  private String produtoCapaDesc;
  private Long numeroNota;
  private Double quantidade;
  private Long ordemCompraId;
  private Double precoCompra;
  private String observacao;
}

