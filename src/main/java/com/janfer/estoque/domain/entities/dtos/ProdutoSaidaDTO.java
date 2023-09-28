package com.janfer.estoque.domain.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.enums.Setor;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProdutoSaidaDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long sku;
  private Date dataSaida;
  private Long quantidade;
  private String retiradoPor;
  private Setor setor;
  private String observacao;

}
