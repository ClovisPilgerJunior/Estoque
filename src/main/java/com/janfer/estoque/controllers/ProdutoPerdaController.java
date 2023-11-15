package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.dtos.ProdutoPerdaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoPerdaPostDTO;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoPerdaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produto Perda", description = "Endpoint da perda de produtos")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
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
    @Operation(summary = "Cadastrar uma nova perda de produto", description = "Cadastra uma nova perda de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Perda de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<Object> create(@RequestBody ProdutoPerdaPostDTO produtoPerdaPostDTO) {

        Long produtoCapaId = produtoPerdaPostDTO.getProdutoCapa();

        if (!produtoCapaService.existById(produtoCapaId)) {
            return ResponseEntity.badRequest().body("ProdutoCapa correspondente não encontrado.");
        }

        produtoPerdaService.save(mapStructMapper.produtoPerdaToProdutoPerdaDTO(produtoPerdaPostDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    @GetMapping
    @Operation(summary = "Listar todas as perdas de produtos", description = "Recupera a lista de todas as perdas de produtos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de perdas de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoPerdaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoPerdaGetDTOAllToProdutoPerda(produtoPerdaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar perda de produto por ID", description = "Recupera uma perda de produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Perda de produto encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    public ResponseEntity<ProdutoPerdaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);

        return produtoPerdaOptional.map(produtoPerda -> {
            ProdutoPerdaGetDTO produtoPerdaGetDTO = mapStructMapper.produtoPerdaGetDTOToProdutoPerda(produtoPerda);
            return ResponseEntity.status(HttpStatus.OK).body(produtoPerdaGetDTO);
        }).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado com ID: " + id));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir uma perda de produto", description = "Exclui uma perda de produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Perda de produto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    public void delete(@PathVariable(value = "id") Long id) {
        produtoPerdaService.delete(id);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma perda de produto", description = "Atualiza uma perda de produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Perda de produto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoPerdaPostDTO produtoPerdaDTO){
        Optional<ProdutoPerda> produtoPerdaOptional = produtoPerdaService.findById(id);
        if(produtoPerdaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não encontrado");
        }
        var produtoPerda = new ProdutoPerda();
        BeanUtils.copyProperties(produtoPerdaDTO, produtoPerda);
        produtoPerda.setId(produtoPerdaOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoPerdaService.save(produtoPerda));
    }


}


