package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProdutoCapaPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  @NotBlank(message = "A descrição não pode estar vazio!")
  private String description;
  @NotNull(message = "O tipo de produto não pode estar vazio!")
  private TipoProduto tipoProduto;
  @NotNull(message = "A unidade de medida não pode estar vazio!")
  private MedidaUnidade medidaUnidade;
  @NotNull(message = "O fornecedor não pode estar vazio!")
  private Integer fornecedor;
  private Long minimo;
  private Long maximo;
  private boolean ativo;

}
