package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date data = new Date();
  private Long quantidade;
  private String motivo;

}
