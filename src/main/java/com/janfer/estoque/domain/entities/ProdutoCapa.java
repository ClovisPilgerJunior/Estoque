package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ProdutoCapa")
public class ProdutoCapa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sku", nullable = false)
  private Long sku;
  private String description;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "tipo_produto")
  private Set<Integer> tipoProduto = new HashSet<>();
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "medida_unidade")
  private Set<Integer> medidaUnidade = new HashSet<>();
  @ManyToOne
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;
  private Long percas;
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
