package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.entities.dtos.ProdutoEntradaDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoEntradaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoEntrada")
public class ProdutoEntradaController {

    private MapStructMapper mapStructMapper;

    private ProdutoEntradaService produtoEntradaService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProdutoEntradaDTO produtoEntradaDTO) {
        produtoEntradaService.save(mapStructMapper.produtoEntradaToProdutoEntradaDTO(produtoEntradaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoEntradaDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoEntradaAllToProdutoEntrada(produtoEntradaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);
        return produtoEntradaOptional.<ResponseEntity<Object>>map(ProdutoEntrada -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ProdutoEntrada))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Produto não encontrado")
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);
        if (produtoEntradaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }


}


