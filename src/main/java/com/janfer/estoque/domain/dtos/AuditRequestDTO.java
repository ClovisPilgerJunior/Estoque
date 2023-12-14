package com.janfer.estoque.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class AuditRequestDTO {

  private Long revisaoId;
  private Date revisaoData;
  private String ip;
  private String usuario;

}

