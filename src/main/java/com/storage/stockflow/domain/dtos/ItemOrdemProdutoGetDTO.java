package com.storage.stockflow.domain.dtos;

import lombok.Data;

@Data
public class ItemOrdemProdutoGetDTO {
  private Long produtoCapaId;
  private Long numeroNota;
  private String produtoCapaDesc;
  private Double quantidade;
  private Long ordemCompraId;
  private Double precoCompra;
  private String observacao;
}

