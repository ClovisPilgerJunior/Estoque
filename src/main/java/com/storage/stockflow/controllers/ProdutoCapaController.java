package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.ProdutoCapaCalculatedGetDTO;
import com.storage.stockflow.domain.entities.ProdutoCapa;
import com.storage.stockflow.domain.dtos.ProdutoCapaGetDTO;
import com.storage.stockflow.domain.dtos.ProdutoCapaPostDTO;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.services.ProdutoCapaService;
import com.storage.stockflow.services.exceptions.DataIntegrityViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Tag(name = "ProdutoCapa", description = "Endpoint do ProdutoCapa")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/produtoCapa")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_VIEW'))")
public class ProdutoCapaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOCAPA_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_CREATE'))")
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar um novo produto", description = "Cadastra um novo produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    public ResponseEntity<ProdutoCapaPostDTO> create(@Valid @RequestBody ProdutoCapaPostDTO produtoCapaPostDTO) {

        if (produtoCapaService.existByDesc(produtoCapaPostDTO.getDescription())) {
            throw new DataIntegrityViolationException("Produto já cadastrado");
        }

        produtoCapaService.save(mapStructMapper.produtoCapaToProdutoCapaPostDTO(produtoCapaPostDTO));
        //URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produtoCapaPostDTO).toUri();
//    return ResponseEntity.created(uri).body(produtoCapaPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCapaPostDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOCAPA_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_UPDATE'))")
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualiza um produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    public ResponseEntity<ProdutoCapa> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoCapaPostDTO produtoCapaPostDTO) {
        ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(id);

        if (produtoCapaService.existByDescAndIdNot(produtoCapaPostDTO.getDescription(), id)) {
            throw new DataIntegrityViolationException("Já existe um produto com esse nome cadastrado");
        }

        ProdutoCapa produtoCapa = mapStructMapper.produtoCapaToProdutoCapaPostDTO(produtoCapaPostDTO);
        produtoCapa.setId(produtoCapaGetDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoCapaService.save(produtoCapa));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOCAPA_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_DELETE'))")
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir um produto", description = "Exclui um produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public void delete(@PathVariable @Positive @NotNull Long id) {
        produtoCapaService.delete(id);
    }

    @GetMapping("/calculado")
    @Operation(summary = "Listar todos os produtos", description = "Recupera a lista de todos os produtos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoCapaCalculatedGetDTO>> findAll() {
        List<ProdutoCapa> produtoCapas = produtoCapaService.findAll();
        List<ProdutoCapaCalculatedGetDTO> produtoCapaGetDTOs = produtoCapaService.obterProdutoCapaComCalculos(produtoCapas);
        return new ResponseEntity<>(produtoCapaGetDTOs, HttpStatus.OK);
    }

    @GetMapping("/calculado/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Recupera um produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<ProdutoCapaCalculatedGetDTO> findByIdCalculated(@PathVariable(value = "id") Long id) {
        ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(id);

        List<ProdutoCapa> produtoCapas = new ArrayList<>();
        produtoCapas.add(new ProdutoCapa());
        List<ProdutoCapaCalculatedGetDTO> produtoCapaGetDTOs = produtoCapaService.obterProdutoCapaComCalculos(produtoCapas);

        return new ResponseEntity<>(produtoCapaGetDTOs.get(0), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOCAPA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_LIST'))")
    @GetMapping
    @Operation(summary = "Listar todos os produtos capas", description = "Recupera a lista de todos os produtos capa cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de produto capa encontrada com sucesso!")
    public ResponseEntity<List<ProdutoCapaGetDTO>> getAll() {
        return new ResponseEntity<>(mapStructMapper.produtoCapaAllToProdutoCapaDTO(produtoCapaService.findAll()), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOCAPA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOCAPA_LIST'))")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto Capa por ID", description = "Recupera um fornecedor pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto Capa encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    public ProdutoCapaGetDTO findById(@PathVariable Long id) {
        return produtoCapaService.findById(id);
    }

}


