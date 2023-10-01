package com.janfer.estoque.domain.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoEntradaDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  private Double precoCompra;
  private Long Quantidade;
  private String observacao;
  private ProdutoCapa produtoCapa;

}
