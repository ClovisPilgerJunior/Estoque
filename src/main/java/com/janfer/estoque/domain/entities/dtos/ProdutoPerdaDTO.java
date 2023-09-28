package com.janfer.estoque.domain.entities.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long sku;
  private Date data;
  private Long quantidade;
  private String motivo;

}
