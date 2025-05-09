package com.storage.stockflow.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.stockflow.domain.audit.LocalBaseAuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class ProdutoEntrada extends LocalBaseAuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private Long numeroNota;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataEntrega;
  @Min(value = 0, message = "Preço unitário não pode ser negativo")
  private Double precoCompra;
  @Min(value = 0, message = "Quantidade não pode ser negativo")
  private Double quantidade;
  private String observacao;
  @ManyToOne // Define a relação muitos-para-um
  @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false) // Define a coluna de chave estrangeira
  @NotNull(message = "Não é possível lançar entrada sem passar um produto")
  private ProdutoCapa produtoCapa; // Referência para o ProdutoCapa correspondente
  @ManyToOne
  @JoinColumn(name = "ordem_compra_id")
  private OrdemCompra ordemCompra;

}
