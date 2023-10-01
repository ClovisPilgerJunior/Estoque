package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.enums.TipoEmpresa;
import jakarta.validation.constraints.NotNull;
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
