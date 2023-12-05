package com.janfer.estoque.domain.dtos;

import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.domain.enums.Profile;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserPostDTO {

  private String name;
  private String password;
  private Set<Integer> profiles = new HashSet<>();
  private boolean ativo;
}
