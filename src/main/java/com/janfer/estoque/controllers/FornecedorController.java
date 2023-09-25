package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.FornecedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/fornecedor")
public class FornecedorController {

    private MapStructMapper mapStructMapper;

    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> getAll(){
        return new ResponseEntity<>(mapStructMapper.fornecedorAllToFornecedorDTO(fornecedorService.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody FornecedorDTO fornecedorDTO){
        fornecedorService.save(mapStructMapper.fornecedorTofornecedorDTO(fornecedorDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
