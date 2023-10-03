package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.enums.Setor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoSaidaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date dataSaida = new Date();
  private Long quantidade;
  private String retiradoPor;
  private Setor setor;
  private String observacao;

}
