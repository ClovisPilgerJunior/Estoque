package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.services.FornecedorService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/fornecedor")
public class FornecedorController {

    private MapStructMapper mapStructMapper;

    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody FornecedorDTO fornecedorDTO){

        if (fornecedorService.existsByEmpresa(fornecedorDTO.getEmpresa())) {
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
                        .body("Fornecedor não encontrado")
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        if(fornecedorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
        var fornecedor = new Fornecedor();
        BeanUtils.copyProperties(fornecedorDTO, fornecedor);
        fornecedor.setId(fornecedorOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.save(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        if(fornecedorOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
        fornecedorService.delete(fornecedorOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Fornecedor " + id + " excluído com sucesso!");
    }


}
