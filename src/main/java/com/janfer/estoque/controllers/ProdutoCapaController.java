package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoCapa;
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

    private ProdutoEntradaService produtoEntradaService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ProdutoCapaPostDTO produtoCapaDTO) {

        if(produtoCapaService.existByDesc(produtoCapaDTO.getDesc())){
            throw new DataIntegrityViolationException("Já existe uma descrição de produto igual");
        }
        
        produtoCapaService.save(mapStructMapper.produtoCapaToProdutoCapaDTO(produtoCapaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ProdutoCapaGetDTO>> findAll() {
        return new ResponseEntity<>(MapStructMapper.INSTANCE.produtoCapaAllToProdutoCapaDTO(produtoCapaService.
                findAll()), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ProdutoCapaGetDTO> findById(@PathVariable(value = "id") Long id) {
//        return new ResponseEntity<>(mapStructMapper.produtoCapaToProdutoCapaGetDTO(
//                produtoCapaService.findById(id).orElseThrow(()-> new ObjectNotFoundException("Não encontrado"))), HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
        if (produtoCapaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoCapaService.delete(produtoCapaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoCapaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoCapa> produtoCapaOptional = produtoCapaService.findById(id);
        if (produtoCapaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Produto não encontrado!");
        }

        ProdutoCapa produtoCapa = produtoCapaOptional.get();
        ProdutoCapaGetDTO produtoCapaGetDTO = mapStructMapper.produtoCapaToProdutoCapaGetDTO(produtoCapa);

        // Faça os cálculos necessários e defina os valores aqui
        Double somaEntradas = produtoEntradaService.calcularSomaEntradas(produtoCapa.getId());
        Double ultimoPrecoCompra = produtoEntradaService.recuperarUltimoPrecoCompra(produtoCapa.getId());

        produtoCapaGetDTO.setSomaEntradas(somaEntradas);
        produtoCapaGetDTO.setUltimoPrecoCompra(ultimoPrecoCompra);

        return new ResponseEntity<>(produtoCapaGetDTO, HttpStatus.OK);
    }



}


