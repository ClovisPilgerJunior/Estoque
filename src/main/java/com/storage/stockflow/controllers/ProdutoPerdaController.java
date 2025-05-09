package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.ProdutoCapaGetDTO;
import com.storage.stockflow.domain.entities.ProdutoPerda;
import com.storage.stockflow.domain.dtos.ProdutoPerdaGetDTO;
import com.storage.stockflow.domain.dtos.ProdutoPerdaPostDTO;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.services.ProdutoCapaService;
import com.storage.stockflow.services.ProdutoPerdaService;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Produto Perda", description = "Endpoint da perda de produtos")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/produtoPerda")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_VIEW'))")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoPerdaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoPerdaService produtoPerdaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOPERDA_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_CREATE'))")
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar uma nova perda de produto", description = "Cadastra uma nova perda de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Perda de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<ProdutoPerdaPostDTO> create(@RequestBody ProdutoPerdaPostDTO produtoPerdaPostDTO) {

        Long produtoCapaId = produtoPerdaPostDTO.getProdutoCapa();

        if (!produtoCapaService.existById(produtoCapaId)) {
            throw new ObjectNotFoundException("ProdutoCapa correspondente não encontrado.");
        }

        produtoPerdaService.save(mapStructMapper.produtoPerdaToProdutoPerdaDTO(produtoPerdaPostDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoPerdaPostDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOPERDA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_LIST'))")
    @GetMapping
    @Operation(summary = "Listar todas as perdas de produtos", description = "Recupera a lista de todas as perdas de produtos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de perdas de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoPerdaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoPerdaGetDTOAllToProdutoPerda(produtoPerdaService.findAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOPERDA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_LIST'))")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar perda de produto por ID", description = "Recupera uma perda de produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Perda de produto encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    public ProdutoCapaGetDTO findById(@PathVariable(value = "id") Long id) {
        return produtoCapaService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOPERDA_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_DELETE'))")
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir uma perda de produto", description = "Exclui uma perda de produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Perda de produto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    public void delete(@PathVariable(value = "id") Long id) {
        produtoPerdaService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOPERDA_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOPERDA_UPDATE'))")
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma perda de produto", description = "Atualiza uma perda de produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Perda de produto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Perda de produto não encontrada")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<ProdutoPerda> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoPerdaPostDTO produtoPerdaPostDTO){
        ProdutoPerdaGetDTO produtoPerdeGetDTO = produtoPerdaService.findById(id);

        ProdutoPerda produtoPerda = mapStructMapper.produtoPerdaToProdutoPerdaDTO(produtoPerdaPostDTO);
        produtoPerda.setId(produtoPerdeGetDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoPerdaService.save(produtoPerda));
    }


}


