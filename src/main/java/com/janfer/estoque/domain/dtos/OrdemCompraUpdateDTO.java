package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrdemCompraUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long fornecedorId;
    private List<ItemOrdemProdutoDTO> items;
    private String nomeSolicitante;
    private Date dataPrevisaoEntrega;
    private Long numeroNotaOrdem;
    private String ordemObservacao;
}
