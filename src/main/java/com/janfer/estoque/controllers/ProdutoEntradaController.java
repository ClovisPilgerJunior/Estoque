package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.ProdutoEntradaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoEntradaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoEntradaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Produto Entrada", description = "Endpoint da entrada de produtos")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
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
    @Operation(summary = "Listar todas as entradas de produtos", description = "Recupera a lista de todas as entradas de produtos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de entradas de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoEntradaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoEntradaGetAllToProdutoEntrada(produtoEntradaService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrada de produto por ID", description = "Recupera uma entrada de produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    public ResponseEntity<ProdutoEntradaGetDTO> findById(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);

        return produtoEntradaOptional.map(produtoEntrada -> {
            ProdutoEntradaGetDTO produtoEntradaGetDTO = mapStructMapper.produtoEntradaToProdutoEntradaGetDTO(produtoEntrada);
            return ResponseEntity.status(HttpStatus.OK).body(produtoEntradaGetDTO);
        }).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado com ID: " + id));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir uma entrada de produto", description = "Exclui uma entrada de produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ProdutoEntrada> produtoEntradaOptional = produtoEntradaService.findById(id);
        if (produtoEntradaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoEntradaService.delete(produtoEntradaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto " + id + " excluído com sucesso");
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar uma nova entrada de produto", description = "Cadastra uma nova entrada de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    @Schema( implementation = ProdutoEntradaPostDTO.class)
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
    @Operation(summary = "Atualizar uma entrada de produto", description = "Atualiza uma entrada de produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
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


