package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.entities.dtos.*;
import com.janfer.estoque.domain.entities.dtos.ProdutoSaidaGetDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoSaidaGetDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoSaidaService;
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
@RequestMapping(value = "/api/produtoSaida")
public class ProdutoSaidaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoSaidaService produtoSaidaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> create(@RequestBody ProdutoSaidaPostDTO produtoSaidaDTO) {

        if (produtoSaidaDTO.getProdutoCapa().getId() == null) {
            return ResponseEntity.badRequest().body("ProdutoCapa não pode ser nulo.");
        }

        Long produtoCapaId = produtoSaidaDTO.getProdutoCapa().getId();

        if (!produtoSaidaDTO.getProdutoCapa().isAtivo()){
            return ResponseEntity.badRequest().body("Não é possível lançar entrada para um produto inativo");
        }

        if (!produtoCapaService.existById(produtoCapaId)) {
            return ResponseEntity.badRequest().body("ProdutoCapa correspondente não encontrado.");
        }

        produtoSaidaService.save(mapStructMapper.produtoSaidaToProdutoSaidaDTO(produtoSaidaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoSaidaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.
            produtoSaidaGetDTOAllToProdutoSaida(produtoSaidaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoSaidaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoSaida> produtoSaidaOptional = produtoSaidaService.findById(id);

        return produtoSaidaOptional.map(produtoSaida -> {
            ProdutoSaidaGetDTO produtoSaidaGetDTO = mapStructMapper.produtoSaidaGetDTOToProdutoSaida(produtoSaida);
            return ResponseEntity.status(HttpStatus.OK).body(produtoSaidaGetDTO);
        }).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado com ID: " + id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoSaida> produtoSaidaOptional = produtoSaidaService.findById(id);
        if (produtoSaidaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoSaidaService.delete(produtoSaidaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoSaidaPostDTO produtoSaidaDTO){
        Optional<ProdutoSaida> produtoSaidaOptional = produtoSaidaService.findById(id);
        if(produtoSaidaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
        var produtoSaida = new ProdutoSaida();
        BeanUtils.copyProperties(produtoSaidaDTO, produtoSaida);
        produtoSaida.setId(produtoSaidaOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoSaidaService.save(produtoSaida));
    }


}


