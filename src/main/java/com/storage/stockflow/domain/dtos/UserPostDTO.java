package com.storage.stockflow.domain.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserPostDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String name;
  private String password;
  private Set<Integer> profiles = new HashSet<>();
  private boolean ativo;
  private final Instant lastModifiedDate;
  private final String lastModifiedBy;

}
