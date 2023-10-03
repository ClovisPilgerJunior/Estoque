package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Date data = new Date();
  private Long quantidade;
  private String motivo;
  private ProdutoCapa produtoCapa;

}
