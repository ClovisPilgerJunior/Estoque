package com.janfer.estoque.domain.entities;

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
public class ItemOrdemCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrdemCompra ordemCompra;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private ProdutoCapa produtoCapa;

  private int quantity;

  // outros campos e métodos conforme necessário
}
