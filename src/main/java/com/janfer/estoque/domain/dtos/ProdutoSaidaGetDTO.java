package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.enums.Setor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoSaidaGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String produtoCapa;
  private String produtoCapaDesc;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataSaida;
  private Long quantidade;
  private String retiradoPor;
  private String setor;
  private String unidadeProdutiva;
  private String servico;
  private String observacao;

}
