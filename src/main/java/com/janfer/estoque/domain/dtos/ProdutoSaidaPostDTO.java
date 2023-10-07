package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.Setor;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoSaidaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date dataSaida = new Date();
  private Long quantidade;
  private String retiradoPor;
  private Setor setor;
  private String observacao;
  private transient ProdutoCapa produtoCapa;

}
