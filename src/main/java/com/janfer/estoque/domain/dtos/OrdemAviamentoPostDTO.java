package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.janfer.estoque.domain.entities.Combinacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Data
public class OrdemAviamentoPostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long numeroOP;
    private Date dataOrdemAviamento;
    private String descOrdemAviamento;
    private Long quantidadeOrdemAviamento;
    private Double precoUnitarioOrdemAviamento;
    private String tecidoOrdemAviamento;
    private String statusOrdemAviamento;
    private List<ItemOrdemAviamentoPostDTO> itemOrdemAviamento = new ArrayList<>();
    private List<CombinacaoPostDTO> combinacoes = new ArrayList<>();


}
