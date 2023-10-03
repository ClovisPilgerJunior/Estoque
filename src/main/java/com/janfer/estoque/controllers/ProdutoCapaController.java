package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaPostDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoEntradaService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoCapa")
public class ProdutoCapaController {

  private MapStructMapper mapStructMapper;

  private ProdutoCapaService produtoCapaService;

  private ProdutoEntradaService produtoEntradaService;

  @PostMapping("/cadastrar")
  public ResponseEntity<Object> create(@Valid @RequestBody ProdutoCapaPostDTO produtoCapaDTO) {

    if (produtoCapaService.existByDesc(produtoCapaDTO.getDesc())) {
      throw new DataIntegrityViolationException("Já existe uma descrição de produto igual");
    }

    produtoCapaService.save(mapStructMapper.produtoCapaToProdutoCapaDTO(produtoCapaDTO));
    return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoCapaPostDTO produtoCapaPostDTO){
    Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
    if(produtoCapaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
    }
    var produtoCapa = new ProdutoCapa();
    BeanUtils.copyProperties(produtoCapaPostDTO, produtoCapa);
    produtoCapa.setId(produtoCapaOptional.get().getId());
    return ResponseEntity.status(HttpStatus.OK).body(produtoCapaService.save(produtoCapa));
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
    if (produtoCapaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }
    produtoCapaService.delete(produtoCapaOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
  }

  @GetMapping
  public ResponseEntity<List<ProdutoCapaGetDTO>> findAll() {
    List<ProdutoCapa> produtoCapas = produtoCapaService.findAll();
    List<ProdutoCapaGetDTO> produtoCapaGetDTOs = produtoCapaService.obterProdutoCapaComCalculos(produtoCapas);
    return new ResponseEntity<>(produtoCapaGetDTOs, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdutoCapaGetDTO> findById(@PathVariable(value = "id") Long id) {
    Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
    if (produtoCapaOptional.isEmpty()) {
      throw new ObjectNotFoundException("Produto não encontrado!");
    }

    List<ProdutoCapa> produtoCapas = new ArrayList<>();
    produtoCapas.add(produtoCapaOptional.get());
    List<ProdutoCapaGetDTO> produtoCapaGetDTOs = produtoCapaService.obterProdutoCapaComCalculos(produtoCapas);

    return new ResponseEntity<>(produtoCapaGetDTOs.get(0), HttpStatus.OK);
  }


}


