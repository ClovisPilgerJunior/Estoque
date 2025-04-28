package com.storage.stockflow.domain.dtos;

import com.storage.stockflow.domain.enums.TipoEmpresa;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FornecedorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotEmpty(message = "O campo EMPRESA não pode estar vazio")
    private String empresa;
    private String nome;
    private TipoEmpresa tipoEmpresa;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

}
