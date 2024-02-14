package com.janfer.estoque.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @OneToMany(mappedBy = "ordemCompra", cascade = CascadeType.ALL)
  private List<ItemOrdemCompra> itemOrdemCompras = new ArrayList<>();

  // outros campos e métodos conforme necessário
}
