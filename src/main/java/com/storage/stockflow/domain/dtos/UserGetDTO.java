package com.storage.stockflow.domain.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserGetDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String name;
  private String password;
  private Set<Integer> profiles = new HashSet<>();
  private boolean ativo;

}
