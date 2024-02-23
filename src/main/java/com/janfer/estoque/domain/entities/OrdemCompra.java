package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.janfer.estoque.domain.enums.StatusOrdem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited(targetAuditMode = NOT_AUDITED)
public class  OrdemCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @OneToMany(mappedBy = "ordemCompra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ItemOrdemCompra> itemOrdemCompras = new ArrayList<>();

  private StatusOrdem statusOrdem;


}
