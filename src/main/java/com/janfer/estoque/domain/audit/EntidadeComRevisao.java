package com.janfer.estoque.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadeComRevisao<T> {

  private Revisao revisao;

  private T entidade;

}