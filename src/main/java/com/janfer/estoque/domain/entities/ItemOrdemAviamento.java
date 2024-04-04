package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.janfer.estoque.domain.enums.Setor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ItemOrdemAviamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String produtoCapaDesc;
    private Date dataSaida;
    @Min(value = 0, message = "Quantidade não pode ser negativo")
    private Long quantidade;
    @NotBlank(message = "Por favor informar a pessoa que está retirando os produtos")
    private String retiradoPor;
    @NotNull(message = "Por favor informe um setor")
    private Setor setor;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "unidade_produtiva_id")
//    private UnidadeProdutiva unidadeProdutiva;
//    private String observacao;
    @ManyToOne
    @JoinColumn(name = "produto_capa_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Não é possível lançar saída sem passar um produto")
    private ProdutoCapa produtoCapa;
    @ManyToOne
    @JoinColumn(name = "ordem_aviamento_id")
    private OrdemAviamento ordemAviamento;

}
