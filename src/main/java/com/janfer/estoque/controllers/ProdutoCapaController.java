package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoCapa")
public class ProdutoCapaController {

  private MapStructMapper mapStructMapper;

  private ProdutoCapaService produtoCapaService;

  @GetMapping
  public ResponseEntity<List<ProdutoCapaDTO>> findAll(){
    return new ResponseEntity<>(mapStructMapper.produtoCapaToProdutocapaDTO(produtoCapaService.findAll()), HttpStatus.OK);
  }

}
