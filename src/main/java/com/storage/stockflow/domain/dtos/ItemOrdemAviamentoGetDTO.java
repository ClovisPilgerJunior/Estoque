package com.storage.stockflow.domain.dtos;

import com.storage.stockflow.domain.enums.Setor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class ItemOrdemAviamentoGetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String produtoCapaDesc;
    private Date dataSaida;
    private Long quantidade;
    private String retiradoPor;
    private Setor setor;
//    private String unidadeProdutiva;
    private String observacao;
    private Long produtoCapa;
    private Long ordemAviamento;

}
