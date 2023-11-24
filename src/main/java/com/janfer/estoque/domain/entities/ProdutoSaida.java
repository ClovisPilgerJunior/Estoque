package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.enums.Setor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoSaida {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataSaida;
  @Min(value = 0, message = "Quantidade não pode ser negativo")
  private Long quantidade;
  @NotBlank(message = "Por favor informar a pessoa que está retirando os produtos")
  private String retiradoPor;
  @NotNull(message = "Por favor informe um setor")
  private Setor setor;
  @ManyToOne(targetEntity = ObjectUtils.Null)
  @JoinColumn(name = "unidade_produtiva_id")
  private UnidadeProdutiva unidadeProdutiva;
  private String observacao;
  @ManyToOne
  @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false)
  @NotNull(message = "Não é possível lançar saída sem passar um produto")
  private ProdutoCapa produtoCapa;
}
