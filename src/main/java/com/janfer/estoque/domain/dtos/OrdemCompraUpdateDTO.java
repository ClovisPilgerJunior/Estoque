package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class OrdemCompraUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long fornecedorId;
    private List<ItemOrdemProdutoDTO> items;
    private Long numeroNota;
    private String observacao;
}
