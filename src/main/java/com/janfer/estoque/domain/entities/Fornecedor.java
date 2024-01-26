package com.janfer.estoque.domain.entities;

import com.janfer.estoque.domain.audit.LocalBaseAuditEntity;
import com.janfer.estoque.domain.enums.TipoEmpresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Fornecedor extends LocalBaseAuditEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(unique = true)
  private String empresa;
  private String nome;
  private TipoEmpresa tipoEmpresa;
  private String email;
  private String telefone;
  private String endereco;
  private boolean ativo;

}
