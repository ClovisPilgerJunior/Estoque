package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaPostDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoCapa")
public class ProdutoCapaController {

    private MapStructMapper mapStructMapper;

    private ProdutoCapaService produtoCapaService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProdutoCapaPostDTO produtoCapaDTO) {
        
        produtoCapaService.save(mapStructMapper.produtoCapaDTOToProdutoCapa(produtoCapaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoCapaPostDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoCapaAllToProdutoCapaDTO(produtoCapaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
        return produtoCapaOptional.<ResponseEntity<Object>>map(ProdutoCapa -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ProdutoCapa))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Produto não encontrado")
                );
    }

//    @GetMapping("/teste/{idProduto}")
//    public ProdutoCapaGetDTO consultarEstoque(@PathVariable Long idProduto) {
//        return produtoCapaService.consultarEstoquePorId(idProduto);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
        if (produtoCapaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }


}


