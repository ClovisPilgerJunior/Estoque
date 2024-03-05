package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.enums.StatusOrdem;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrdemCompraDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPedidoOrdemCompra;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataRecebimentoOrdemCompra;
    private Integer statusOrdem;
    private Integer quantidade;
    private Double valorTotal;
}
