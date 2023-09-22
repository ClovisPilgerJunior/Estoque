package com.janfer.estoque.domain.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoEntradaDTO implements Serializable {

  @Autowired
  private ProdutoCapa produtoCapa;

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private int sku;
  private ProdutoCapa description;
  private ProdutoCapa tipoProduto;
  private ProdutoCapa MedidaUnidade;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  private Double precoCompra;
  private Long Quantidade;
  private String observacao;
}
