package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoPerdaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date data;
  private Double quantidade;
  private String motivo;
  @NotNull(message = "Produto capa não pode estar vazio")
  private Long produtoCapa;

}
