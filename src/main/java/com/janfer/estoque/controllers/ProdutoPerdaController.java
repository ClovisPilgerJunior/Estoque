package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.entities.dtos.ProdutoPerdaDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoPerdaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoPerda")
public class ProdutoPerdaController {

    private MapStructMapper mapStructMapper;

    private ProdutoPerdaService produtoPerdaService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProdutoPerdaDTO produtoPerdaDTO) {
        produtoPerdaService.save(mapStructMapper.produtoPerdaToProdutoPerdaDTO(produtoPerdaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoPerdaDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoPerdaDTOAllToProdutoPerda(produtoPerdaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);
        return produtoPerdaOptional.<ResponseEntity<Object>>map(ProdutoPerda -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ProdutoPerda))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Produto não encontrado")
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);
        if (produtoPerdaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoPerdaService.delete(produtoPerdaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }


}


