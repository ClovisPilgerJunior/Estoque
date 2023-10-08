package com.janfer.estoque.controllers;

import com.janfer.estoque.controllers.messages.FornecedorMessage;
import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.dtos.FornecedorDTO;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.FornecedorService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/fornecedor")
public class FornecedorController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    FornecedorService fornecedorService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> create(@Valid @RequestBody FornecedorDTO fornecedorDTO){

        if (fornecedorService.existByEmpresa(fornecedorDTO.getEmpresa())) {
            throw new DataIntegrityViolationException("Violação na integridade dos dados");
        }

        fornecedorService.save(mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Fornecedor cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> getAll(){
        return new ResponseEntity<>(mapStructMapper.fornecedorAllToFornecedorDTO(fornecedorService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id")Long id){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        return fornecedorOptional.<ResponseEntity<Object>>map(fornecedor -> ResponseEntity
                .status(HttpStatus.OK)
                .body(fornecedor))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(FornecedorMessage.NOT_FOUND)
                );
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        if(fornecedorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FornecedorMessage.NOT_FOUND);
        }

        Fornecedor fornecedor = mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO);
        fornecedor.setId(fornecedorOptional.get().getId());

        if(fornecedorService.existByEmpresaAndIdNot(fornecedorDTO.getEmpresa(), id)){
            throw new DataIntegrityViolationException("Já existe um fornecedor com esse nome cadastrado");
        }


        return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.save(fornecedor));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        if(fornecedorOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FornecedorMessage.NOT_FOUND);
        }
        fornecedorService.delete(fornecedorOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Fornecedor " + id + " excluído com sucesso!");
    }


}
