package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.TipoProduto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String description;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  private Integer fornecedor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
