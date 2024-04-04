package com.janfer.estoque.domain.dtos;

import lombok.Data;

@Data
public class CombinacaoDetalheGetDTO {
    private Long id;
    private String peca;
    private String aviamento;
    // Getters e setters
}
