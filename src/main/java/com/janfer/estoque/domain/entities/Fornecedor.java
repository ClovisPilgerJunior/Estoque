package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.entities.enums.TipoEmpresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private String empresa;
  private String nome;
  private TipoEmpresa tipoEmpresa;
  private String email;
  private String telefone;
  private String endereco;
  private boolean ativo;
  @ManyToOne
  @JoinColumn(name = "produto_capa_sku")
  private ProdutoCapa produtoCapa;

}
