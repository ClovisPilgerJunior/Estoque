package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long cod;
  private Long codSistema;
  private String description;
  private TipoProduto tipoProduto;
  private MedidaUnidade medidaUnidade;
  @OneToMany
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;
  private Long entrada;
  private Long saida;
  private Long percas;
  private Long saldo;
  private Double precoUnitario;
  private Double valor;
  private Long minimo;
  private Long maximo;
  private Resuprimento resuprimento;
  private boolean ativo;

}
