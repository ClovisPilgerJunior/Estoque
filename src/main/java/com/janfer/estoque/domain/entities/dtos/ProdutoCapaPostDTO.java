package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long sku;
  private String description;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  private Fornecedor fornecedor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
