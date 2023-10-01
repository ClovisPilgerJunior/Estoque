package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.Setor;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
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
public class ProdutoSaida {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataSaida;
  private Long quantidade;
  private String retiradoPor;
  private Setor setor;
  private String observacao;

}
