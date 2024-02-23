package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoEntradaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  private Double precoCompra;
  private Long quantidade;
  private String observacao;
  @NotNull(message = "Produto capa não pode estar vazio")
  private Long produtoCapa;
  private ItemOrdemCompra itemOrdemCompra;

}
