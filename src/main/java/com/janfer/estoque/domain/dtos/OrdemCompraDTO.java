package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.enums.StatusOrdem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrdemCompraDTO {
    private Long id;
    private StatusOrdem statusOrdem;
}
