package com.storage.stockflow.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.stockflow.domain.audit.LocalBaseAuditEntity;
import com.storage.stockflow.domain.enums.Setor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class ProdutoSaida extends LocalBaseAuditEntity {
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
  @ManyToOne
  @JoinColumn(name = "unidade_produtiva_id")
  private UnidadeProdutiva unidadeProdutiva;
  private String observacao;
  @ManyToOne
  @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false)
  @NotNull(message = "Não é possível lançar saída sem passar um produto")
  private ProdutoCapa produtoCapa;
  @ManyToOne
  @JoinColumn(name = "ordem_aviamento_id")
  private OrdemAviamento ordemAviamento;
}
