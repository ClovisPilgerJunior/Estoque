package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoCapaPostDTO;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoEntradaService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Tag(name = "ProdutoCapa", description = "Endpoint do ProdutoCapa")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/produtoCapa")
public class ProdutoCapaController {

  @Autowired
  MapStructMapper mapStructMapper;

  @Autowired
  ProdutoCapaService produtoCapaService;


  @PostMapping("/cadastrar")
  @Operation(summary = "Cadastrar um novo produto", description = "Cadastra um novo produto com base nos dados fornecidos.")
  @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
  @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
  public ResponseEntity<Object> create(@Valid @RequestBody ProdutoCapaPostDTO produtoCapaDTO) {

    if(produtoCapaService.existByDesc(produtoCapaDTO.getDesc())){
      throw new DataIntegrityViolationException("Produto já cadastrado");
    }

    produtoCapaService.save(mapStructMapper.produtoCapaToProdutoCapaDTO(produtoCapaDTO));
    return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
  }

  @PutMapping("/atualizar/{id}")
  @Operation(summary = "Atualizar um produto", description = "Atualiza um produto existente com base nos dados fornecidos.")
  @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
  @ApiResponse(responseCode = "404", description = "Produto não encontrado")
  @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
  public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoCapaPostDTO produtoCapaPostDTO){
    Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
    if(produtoCapaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }

    if(produtoCapaService.existByDescAndIdNot(produtoCapaPostDTO.getDesc(), id)){
      throw new DataIntegrityViolationException("Já existe um produto com esse nome cadastrado");
    }

    var produtoCapa = new ProdutoCapa();
    BeanUtils.copyProperties(produtoCapaPostDTO, produtoCapa);
    produtoCapa.setId(produtoCapaOptional.get().getId());
    return ResponseEntity.status(HttpStatus.OK).body(produtoCapaService.save(produtoCapa));
  }

  @DeleteMapping("/deletar/{id}")
  @Operation(summary = "Excluir um produto", description = "Exclui um produto existente com base no ID fornecido.")
  @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso")
  @ApiResponse(responseCode = "404", description = "Produto não encontrado")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
    Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
    if (produtoCapaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }
    produtoCapaService.delete(produtoCapaOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
  }

  @GetMapping
  @Operation(summary = "Listar todos os produtos", description = "Recupera a lista de todos os produtos cadastrados.")
  @ApiResponse(responseCode = "200", description = "Lista de produtos encontrada com sucesso")
  public ResponseEntity<List<ProdutoCapaGetDTO>> findAll() {
    List<ProdutoCapa> produtoCapas = produtoCapaService.findAll();
    List<ProdutoCapaGetDTO> produtoCapaGetDTOs = produtoCapaService.obterProdutoCapaComCalculos(produtoCapas);
    return new ResponseEntity<>(produtoCapaGetDTOs, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar produto por ID", description = "Recupera um produto pelo seu ID.")
  @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso")
  @ApiResponse(responseCode = "404", description = "Produto não encontrado")
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


