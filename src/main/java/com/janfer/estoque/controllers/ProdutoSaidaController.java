package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.entities.dtos.ProdutoSaidaDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoSaidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoSaida")
public class ProdutoSaidaController {

    private MapStructMapper mapStructMapper;

    private ProdutoSaidaService produtoSaidaService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProdutoSaidaDTO produtoSaidaDTO) {
        produtoSaidaService.save(mapStructMapper.produtoSaidaToProdutoSaidaDTO(produtoSaidaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoSaidaDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoSaidaDTOAllToProdutoSaida(produtoSaidaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoSaida> produtoSaidaOptional = produtoSaidaService.findById(id);
        return produtoSaidaOptional.<ResponseEntity<Object>>map(ProdutoSaida -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ProdutoSaida))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Produto não encontrado")
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoSaida> produtoSaidaOptional = produtoSaidaService.findById(id);
        if (produtoSaidaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }


}


