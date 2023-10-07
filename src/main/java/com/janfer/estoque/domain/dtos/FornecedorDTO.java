package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.enums.TipoEmpresa;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FornecedorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String empresa;
    private String nome;
    private TipoEmpresa tipoEmpresa;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

}
