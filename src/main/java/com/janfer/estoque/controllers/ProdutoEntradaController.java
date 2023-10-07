package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.dtos.ProdutoEntradaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoEntradaPostDTO;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoEntradaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/produtoEntrada")
public class ProdutoEntradaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoEntradaService produtoEntradaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @GetMapping
    public ResponseEntity<List<ProdutoEntradaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoEntradaGetAllToProdutoEntrada(produtoEntradaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEntradaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);

        return produtoEntradaOptional.map(produtoEntrada -> {
            ProdutoEntradaGetDTO produtoEntradaGetDTO = mapStructMapper.produtoEntradaToProdutoEntradaGetDTO(produtoEntrada);
            return ResponseEntity.status(HttpStatus.OK).body(produtoEntradaGetDTO);
        }).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado com ID: " + id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);
        if (produtoEntradaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoEntradaService.delete(produtoEntradaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> criarProdutoEntrada(@RequestBody ProdutoEntradaPostDTO produtoEntradaDTO) {
        // Verificar se o ProdutoCapa no DTO está nulo
        if (produtoEntradaDTO.getProdutoCapa().getId() == null) {
            return ResponseEntity.badRequest().body("ProdutoCapa não pode ser nulo.");
        }

        // Verificar se o ProdutoCapa existe
        Long produtoCapaId = produtoEntradaDTO.getProdutoCapa().getId();
        if (!produtoCapaService.existById(produtoCapaId)) {
            return ResponseEntity.badRequest().body("ProdutoCapa correspondente não encontrado.");
        }

        produtoEntradaService.save(mapStructMapper.produtoEntradaToProdutoEntradaDTO(produtoEntradaDTO));

        return ResponseEntity.ok("ProdutoEntrada cadastrado com sucesso.");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoEntradaPostDTO produtoEntradaDTO){
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);
        if(produtoEntradaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
        var produtoEntrada = new ProdutoEntrada();
        BeanUtils.copyProperties(produtoEntradaDTO, produtoEntrada);
        produtoEntrada.setId(produtoEntradaOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoEntradaService.save(produtoEntrada));
    }

}


