package com.janfer.estoque.controllers.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum FornecedorMessage {

    NOT_FOUND("Fornecedor não encontrado");

    private final String desc;

}
