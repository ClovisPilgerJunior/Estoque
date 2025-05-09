package com.storage.stockflow.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.stockflow.domain.enums.StatusOrdem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited(targetAuditMode = NOT_AUDITED)
public class  OrdemCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;
  private String nomeSolicitante;
  private Long numeroNotaOrdem;
  private Date dataEmissao;
  private Date dataPedidoOrdemCompra;
  private Date dataRecebimentoOrdemCompra;
  private Date dataPrevisaoEntrega;

  @ManyToOne
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;

  @JsonIgnore
  @OneToMany(mappedBy = "ordemCompra", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<ItemOrdemCompra> itemOrdemCompras = new ArrayList<>();

  private StatusOrdem statusOrdem;

  private Double valorTotal;

  private String ordemObservacao;

  public int getQuantidadeItens() {
    return itemOrdemCompras.size();
  }

  // Método para calcular o valor total
  public Double calcularValorTotal() {
    return itemOrdemCompras.stream()
            .mapToDouble(ItemOrdemCompra::getValorTotalItem)
            .sum();
  }

}
