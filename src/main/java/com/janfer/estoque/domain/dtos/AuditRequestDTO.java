package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuditRequestDTO {
  private String property;
  private String propertyValue;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate startDate;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate endDate;

  // getters e setters
}

