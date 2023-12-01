package com.janfer.estoque.domain.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserGetDTO {
  private Integer id;
  private String name;
  private String password;
  private Set<String> profiles = new HashSet<>();

}
