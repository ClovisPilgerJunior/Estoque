package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.Date;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited(targetAuditMode = NOT_AUDITED)
public class ItemOrdemCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String produtoCapaDesc;

  private Long numeroNota;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataPedido;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataRecebimento;

  @Min(value = 0, message = "Preço unitário não pode ser negativo")
  private Double precoCompra;

  @ManyToOne
  @JoinColumn(name = "ordem_compra_id")
  private OrdemCompra ordemCompra;

  @ManyToOne
  @JoinColumn(name = "produto_capa_id")
  private ProdutoCapa produtoCapa;

  private Double quantidade;

  private Double valorTotalOrdem;

  private String observacao;

  public double getValorTotalItem() {
    Double valor = precoCompra != null ? getPrecoCompra() * getQuantidade() : 0;
    return valor;
  }

  public void setPrecoCompra(){
    this.precoCompra = precoCompra != null ? getPrecoCompra() : 0;
  }

}
