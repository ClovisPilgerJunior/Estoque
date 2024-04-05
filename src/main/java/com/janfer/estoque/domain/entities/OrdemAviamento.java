package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.janfer.estoque.domain.enums.StatusOrdemAviamento;
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
    private StatusOrdemAviamento statusOrdemAviamento;
    @OneToMany(mappedBy = "ordemAviamento", orphanRemoval = true)
    private List<ItemOrdemAviamento> itemOrdemAviamento = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "ordemAviamento", orphanRemoval = true)
    private List<Combinacao> combinacoes = new ArrayList<>();


}
