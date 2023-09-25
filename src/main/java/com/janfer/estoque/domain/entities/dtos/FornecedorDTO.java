package com.janfer.estoque.domain.entities.dtos;

import com.janfer.estoque.domain.entities.ProdutoCapa;
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
    private Integer tipoEmpresa;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;
    private ProdutoCapa produtoCapa;

}
