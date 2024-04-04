package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CombinacaoPostDTO {
    private String titulo;
    private List<CombinacaoDetalhePostDTO> combinacoesDetalhes;
    // Getters e setters
}
