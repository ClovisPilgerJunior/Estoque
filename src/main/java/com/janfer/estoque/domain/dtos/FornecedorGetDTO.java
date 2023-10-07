package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FornecedorGetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String empresa;
    private String nome;

}
