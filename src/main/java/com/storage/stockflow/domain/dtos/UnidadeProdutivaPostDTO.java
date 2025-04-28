package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UnidadeProdutivaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private String nome;
  private String servico;
  private Boolean ativo;

}
