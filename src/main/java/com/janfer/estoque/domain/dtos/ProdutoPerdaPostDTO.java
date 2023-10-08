package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Date data = new Date();
  private Long quantidade;
  private String motivo;
  private transient ProdutoCapa produtoCapa;

}
