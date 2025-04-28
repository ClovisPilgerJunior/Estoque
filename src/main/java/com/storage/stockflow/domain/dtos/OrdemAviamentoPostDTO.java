package com.storage.stockflow.domain.dtos;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrdemAviamentoPostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
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
