package com.storage.stockflow.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private int codSistema;
  @NotBlank(message = "A descrição não pode estar vazio!")
  private String description;
  @NotNull(message = "O tipo de produto não pode estar vazio!")
  private Integer tipoProduto;
  @NotNull(message = "A unidade de medida não pode estar vazio!")
  private Integer medidaUnidade;
  @NotNull(message = "O fornecedor não pode estar vazio!")
  private Long fornecedor;
  private Long minimo;
  private Long maximo;
  private boolean ativo;

}
