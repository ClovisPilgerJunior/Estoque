package com.storage.stockflow.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CombinacaoPostDTO {
    private Long id;
    private String titulo;
    private List<CombinacaoDetalhePostDTO> combinacoesDetalhes;
    // Getters e setters
}
