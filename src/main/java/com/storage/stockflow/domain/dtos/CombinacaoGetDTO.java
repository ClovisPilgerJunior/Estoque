package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CombinacaoGetDTO {
    private Long id;
    private String titulo;
    private List<CombinacaoDetalheGetDTO> combinacoesDetalhes;
    // Getters e setters
}

