package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String desc;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  private FornecedorGetDTO fornecedor;
  private Double somaEntradas;
  private Double ultimoPrecoCompra;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
