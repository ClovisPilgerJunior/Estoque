package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @Column(unique = true)
  private String description;
  @Column(nullable = false)
  private TipoProduto tipoProduto;
  @Column(nullable = false)
  private MedidaUnidade medidaUnidade;
  @ManyToOne
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
