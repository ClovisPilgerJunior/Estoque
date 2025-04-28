package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.OrdemAviamentoGetDTO;
import com.storage.stockflow.domain.dtos.OrdemAviamentoPostDTO;
import com.storage.stockflow.services.OrdemAviamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/ordemAviamento")
public class OrdemAviamentoController {

    @Autowired
    private OrdemAviamentoService ordemAviamentoService;

    @PostMapping
    public ResponseEntity<OrdemAviamentoGetDTO> createOrdemAviamento(@RequestBody OrdemAviamentoPostDTO ordemAviamentoPostDTO) {
        OrdemAviamentoGetDTO ordemAviamentoGetDTO = ordemAviamentoService.createOrdemAviamento(ordemAviamentoPostDTO);
        return new ResponseEntity<>(ordemAviamentoGetDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrdemAviamentoGetDTO>> getAllOrdemAviamentos() {
        List<OrdemAviamentoGetDTO> ordemAviamentos = ordemAviamentoService.getAllOrdemAviamentos();
        return new ResponseEntity<>(ordemAviamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemAviamentoGetDTO> getOrdemAviamentoById(@PathVariable Long id) {
        Optional<OrdemAviamentoGetDTO> ordemAviamento = ordemAviamentoService.getOrdemAviamentoById(id);
        return ordemAviamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemAviamentoGetDTO> updateOrdemAviamento(@PathVariable Long id, @RequestBody OrdemAviamentoPostDTO ordemAviamentoPostDTO) {
        OrdemAviamentoGetDTO ordemAviamentoGetDTO = ordemAviamentoService.updateOrdemAviamento(id, ordemAviamentoPostDTO);
        return new ResponseEntity<>(ordemAviamentoGetDTO, HttpStatus.OK);
    }
}
