package com.janfer.estoque.domain.entities;

import jakarta.persistence.*;
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
public class OrdemAviamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long numeroOP;
    private Date dataOrdemAviamento;
    private String descOrdemAviamento;
    private Long quantidadeOrdemAviamento;
    private Double precoUnitarioOrdemAviamento;
    private String tecidoOrdemAviamento;

}
