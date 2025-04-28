package com.storage.stockflow.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoSaidaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataSaida;
  private Double quantidade;
  private String retiradoPor;
  private Integer setor;
  private Long unidadeProdutiva;
  private String observacao;
  @NotNull(message = "Produto capa não pode estar vazio")
  private Long produtoCapa;

}
