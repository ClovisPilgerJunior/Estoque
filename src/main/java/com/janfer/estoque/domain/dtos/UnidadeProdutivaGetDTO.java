package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UnidadeProdutivaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String nome;
  private String servico;
  private boolean ativo;

}
