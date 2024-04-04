package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.enums.Setor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
