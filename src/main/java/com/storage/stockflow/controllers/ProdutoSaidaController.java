package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.ProdutoSaidaGetDTO;
import com.storage.stockflow.domain.dtos.ProdutoSaidaPostDTO;
import com.storage.stockflow.domain.entities.ProdutoSaida;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.services.ProdutoCapaService;
import com.storage.stockflow.services.ProdutoSaidaService;
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
@Tag(name = "Produto Saida", description = "Endpoint da saida de produtos")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/produtoSaida")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_VIEW'))")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoSaidaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoSaidaService produtoSaidaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOSAIDA_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_CREATE'))")
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar uma nova saída de produto", description = "Cadastra uma nova saída de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Saída de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<ProdutoSaidaPostDTO> create(@Valid @RequestBody ProdutoSaidaPostDTO produtoSaidaDTO) {


        Long produtoCapaId = produtoSaidaDTO.getProdutoCapa();

        if (!produtoCapaService.existById(produtoCapaId)) {
            throw new ObjectNotFoundException("Produto Capa correspondente não encontrado");
        }

        produtoSaidaService.save(mapStructMapper.produtoSaidaToProdutoSaidaDTO(produtoSaidaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSaidaDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOSAIDA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_LIST'))")
    @GetMapping
    @Operation(summary = "Listar todas as saídas de produtos", description = "Recupera a lista de todas as saídas de produtos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de saídas de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoSaidaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.
            produtoSaidaGetDTOAllToProdutoSaida(produtoSaidaService.findAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOSAIDA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_LIST'))")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar saída de produto por ID", description = "Recupera uma saída de produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Saída de produto encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Saída de produto não encontrada")
    public ProdutoSaidaGetDTO findById(@PathVariable(value = "id") Long id) {
        return produtoSaidaService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOSAIDA_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_DELETE'))")
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir uma saída de produto", description = "Exclui uma saída de produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Saída de produto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Saída de produto não encontrada")
    public void delete(@PathVariable(value = "id") Long id) {
        produtoSaidaService.delete(id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOSAIDA_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOSAIDA_UPDATE'))")
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma saída de produto", description = "Atualiza uma saída de produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Saída de produto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Saída de produto não encontrada")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<ProdutoSaida> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoSaidaPostDTO produtoSaidaDTO){
        ProdutoSaidaGetDTO produtoSaidaGetDTO = produtoSaidaService.findById(id);

        if (!produtoCapaService.existById(produtoSaidaDTO.getProdutoCapa())) {
            throw new ObjectNotFoundException("Produto Capa correspondente não encontrado");
        }

        ProdutoSaida produtoSaida = mapStructMapper.produtoSaidaToProdutoSaidaDTO(produtoSaidaDTO);
        produtoSaida.setId(produtoSaidaGetDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoSaidaService.save(produtoSaida));
    }


}


