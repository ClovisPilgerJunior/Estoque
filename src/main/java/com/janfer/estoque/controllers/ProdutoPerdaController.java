package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.entities.dtos.ProdutoPerdaGetDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoPerdaPostDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoPerdaService;
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
@RequestMapping(value = "/api/produtoPerda")
public class ProdutoPerdaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoPerdaService produtoPerdaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> create(@RequestBody ProdutoPerdaPostDTO produtoPerdaPostDTO) {

        if (produtoPerdaPostDTO.getProdutoCapa().getId() == null) {
            return ResponseEntity.badRequest().body("ProdutoCapa não pode ser nulo.");
        }

        Long produtoCapaId = produtoPerdaPostDTO.getProdutoCapa().getId();

        System.out.println(produtoPerdaPostDTO.getProdutoCapa().isAtivo());

        if (!produtoCapaService.existById(produtoCapaId)) {
            return ResponseEntity.badRequest().body("ProdutoCapa correspondente não encontrado.");
        }

        produtoPerdaService.save(mapStructMapper.produtoPerdaToProdutoPerdaDTO(produtoPerdaPostDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoPerdaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoPerdaGetDTOAllToProdutoPerda(produtoPerdaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoPerdaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);

        return produtoPerdaOptional.map(produtoPerda -> {
            ProdutoPerdaGetDTO produtoPerdaGetDTO = mapStructMapper.produtoPerdaGetDTOToProdutoPerda(produtoPerda);
            return ResponseEntity.status(HttpStatus.OK).body(produtoPerdaGetDTO);
        }).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado com ID: " + id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);
        if (produtoPerdaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoPerdaService.delete(produtoPerdaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoPerdaPostDTO produtoPerdaDTO){
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);
        if(produtoPerdaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
        var produtoPerda = new ProdutoPerda();
        BeanUtils.copyProperties(produtoPerdaDTO, produtoPerda);
        produtoPerda.setId(produtoPerdaOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoPerdaService.save(produtoPerda));
    }


}


