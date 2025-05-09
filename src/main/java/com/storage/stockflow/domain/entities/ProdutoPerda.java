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
public class ProdutoPerda extends LocalBaseAuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date data = new Date();
  @Min(value = 0, message = "Quantidade não pode ser negativo")
  private Long quantidade;
  private String motivo;
  @ManyToOne // Define a relação muitos-para-um
  @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false) // Define a coluna de chave estrangeira
  @NotNull(message = "Não é possível lançar perdas sem passar um produto")
  private ProdutoCapa produtoCapa; // Referência para o ProdutoCapa correspondente

}
