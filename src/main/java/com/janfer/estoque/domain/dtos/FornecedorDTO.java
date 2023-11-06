package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.enums.TipoEmpresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Email(message = "Insira um email válido")
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

}
