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

  @Autowired
  private ProdutoEntrada produtoEntrada;

  @Autowired
  private ProdutoSaida produtoSaida;

  @Autowired
  private ProdutoPerda produtoPerda;

  @Serial
  private static final long serialVersionUID = 1L;
  private Long cod;
  private Long codSistema;
  private String description;
  private final Set<Integer> tipoProduto = new HashSet<>();
  private final Set<Integer> medidaUnidade = new HashSet<>();
  private Fornecedor fornecedor;
  private ProdutoEntrada quantidadeEntrada;
  private ProdutoSaida quantidadeSaida;
  private ProdutoPerda quantidadePerda;
  private Long saldo;
  private Double precoUnitario;
  private Double valor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

  public Set<TipoProduto> getTipoProduto() {
    return tipoProduto.stream().map(TipoProduto::toEnum).collect(Collectors.toSet());
  }

  public void addTipoProduto(TipoProduto tipoProduto) {
    this.tipoProduto.add(tipoProduto.getCod());
  }

  public Set<MedidaUnidade> getMedidaUnidade() {
    return medidaUnidade.stream().map(MedidaUnidade::toEnum).collect(Collectors.toSet());
  }

  public void addMedidaUnidade(MedidaUnidade medidaUnidade){
    this.medidaUnidade.add(medidaUnidade.getCod());
  }

}
