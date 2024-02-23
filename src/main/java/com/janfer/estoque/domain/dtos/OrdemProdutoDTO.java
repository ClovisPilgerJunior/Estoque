package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrdemProdutoDTO {
  private Long produtoCapaId;
  private int quantidade;
  private Long itemOrdemCompraId;
}

