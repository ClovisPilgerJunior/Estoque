package com.storage.stockflow.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrdemCompraPostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nomeSolicitante;
    private Long numeroNotaOrdem;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataEmissao;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPedidoOrdemCompra;
    @Null
    private Date dataPrevisaoEntrega;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataRecebimentoOrdemCompra;
    private Long fornecedor;
    private Integer statusOrdem;
    private Integer quantidade;
    private Double valorTotal;
    private String ordemObservacao;
}
