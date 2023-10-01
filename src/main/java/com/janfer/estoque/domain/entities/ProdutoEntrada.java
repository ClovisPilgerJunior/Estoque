package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntrada {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  private Double precoCompra;
  private Long quantidade;
  private String observacao;
  @ManyToOne // Define a relação muitos-para-um
  @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false) // Define a coluna de chave estrangeira
  private ProdutoCapa produtoCapa; // Referência para o ProdutoCapa correspondente

}
