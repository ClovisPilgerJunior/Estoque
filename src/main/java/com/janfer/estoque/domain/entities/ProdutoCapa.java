package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @Column(name = "id", nullable = false)
  private Long id;
  private Long sku;
  private String description;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  @ManyToOne
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;
  private Long saldo;
  private Double precoUnitario;
  private Double valor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}