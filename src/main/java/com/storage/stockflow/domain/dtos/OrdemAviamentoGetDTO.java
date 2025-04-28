package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrdemAviamentoGetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long numeroOP;
    private Date dataOrdemAviamento;
    private String descOrdemAviamento;
    private Long quantidadeOrdemAviamento;
    private Double precoUnitarioOrdemAviamento;
    private String tecidoOrdemAviamento;
    private List<ItemOrdemAviamentoGetDTO> itemOrdemAviamento;
    private List<CombinacaoGetDTO> combinacoes;


}
