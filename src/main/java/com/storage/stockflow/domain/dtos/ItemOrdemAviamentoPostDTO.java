package com.storage.stockflow.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.storage.stockflow.domain.enums.Setor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ItemOrdemAviamentoPostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("produtoCapaDesc")
    private String produtoCapaDesc;
    private Date dataSaida;
    @Min(value = 0, message = "Quantidade não pode ser negativo")
    private Long quantidade;
    @NotBlank(message = "Por favor informar a pessoa que está retirando os produtos")
    private String retiradoPor;
    @NotNull(message = "Por favor informe um setor")
    private Setor setor;
//    private Long unidadeProdutiva;
    private String observacao;
    private Long produtoCapa;
    private Long ordemAviamento;

}
