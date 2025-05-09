package com.storage.stockflow.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.stockflow.domain.enums.StatusOrdemAviamento;
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
    @JsonIgnore
    @OneToMany(mappedBy = "ordemAviamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrdemAviamento> itemOrdemAviamento = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "ordemAviamento", orphanRemoval = true)
    private List<Combinacao> combinacoes = new ArrayList<>();


}
