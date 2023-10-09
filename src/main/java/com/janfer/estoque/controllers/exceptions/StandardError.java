package com.janfer.estoque.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private Long timestamp;
  private Integer status;
  private String error;
  private String message;
  private String path;

}
