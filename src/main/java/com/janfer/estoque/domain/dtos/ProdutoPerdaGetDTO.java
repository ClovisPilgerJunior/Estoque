package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String produtoCapa;
  private String produtoCapaDesc;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date data;
  private Double quantidade;
  private String motivo;

}
