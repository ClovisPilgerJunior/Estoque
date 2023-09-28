package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProdutoCapaDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long sku;
  private String description;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  private Fornecedor fornecedor;
  private Long saldo;
  private Double precoUnitario;
  private Double valor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
