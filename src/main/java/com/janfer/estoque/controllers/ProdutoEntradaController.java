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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Tag(name = "Produto Entrada", description = "Endpoint da entrada de produtos")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/produtoEntrada")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_VIEW'))")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoEntradaController {

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    ProdutoEntradaService produtoEntradaService;

    @Autowired
    ProdutoCapaService produtoCapaService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOENTRADA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_LIST'))")
    @GetMapping
    @Operation(summary = "Listar todas as entradas de produtos", description = "Recupera a lista de todas as entradas de produtos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de entradas de produtos encontrada com sucesso")
    public ResponseEntity<List<ProdutoEntradaGetDTO>> findAll() {
        return new ResponseEntity<>(mapStructMapper.produtoEntradaGetAllToProdutoEntrada(produtoEntradaService.findAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOENTRADA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_LIST'))")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrada de produto por ID", description = "Recupera uma entrada de produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    public ProdutoEntradaGetDTO findById(@PathVariable Long id) {
       return produtoEntradaService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOENTRADA_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_DELETE'))")
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir uma entrada de produto", description = "Exclui uma entrada de produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    public void delete(@PathVariable(value = "id") Long id) {
        produtoEntradaService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOENTRADA_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_CREATE'))")
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar uma nova entrada de produto", description = "Cadastra uma nova entrada de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    @Schema( implementation = ProdutoEntradaPostDTO.class)
    public ResponseEntity<ProdutoEntradaPostDTO> criarProdutoEntrada(@Valid @RequestBody ProdutoEntradaPostDTO produtoEntradaDTO) {

        // Verificar se o ProdutoCapa existe
        Long produtoCapaId = produtoEntradaDTO.getProdutoCapa();
        if (!produtoCapaService.existById(produtoCapaId)) {
            throw new ObjectNotFoundException("Produto capa correspondente não cadastrado");
        }

        produtoEntradaService.save(mapStructMapper.produtoEntradaToProdutoEntradaDTO(produtoEntradaDTO));

        return ResponseEntity.ok(produtoEntradaDTO);
    }

    @PostMapping("/cadastrarAll")
    @Operation(summary = "Cadastrar uma nova entrada de produto", description = "Cadastra uma nova entrada de produto com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    @Schema( implementation = ProdutoEntradaPostDTO.class)
    public ResponseEntity<ProdutoEntrada> criarProdutoEntradaAll(@Valid @RequestBody List<ProdutoEntrada> produtoEntradaDTO) {

    List<ProdutoEntrada> produtoEntradas = mapStructMapper.toProdutoEntrada(produtoEntradaDTO)
            .stream()
                .map(mapStructMapper::produtoEntradaToProdutoEntradaDTO)
                    .collect(Collectors.toList());

        produtoEntradaService.saveAll(produtoEntradas);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(produtoEntradaDTO.get(0).getId()).toUri();
        return ResponseEntity.created(uri).body(produtoEntradaDTO.get(0));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_PRODUTOENTRADA_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_PRODUTOENTRADA_UPDATE'))")
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar uma entrada de produto", description = "Atualiza uma entrada de produto existente com base nos dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Entrada de produto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada de produto não encontrada")
    @ApiResponse(responseCode = "409", description = "Produto está inativado no sistema")
    public ResponseEntity<ProdutoEntrada> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoEntradaPostDTO produtoEntradaPostDTO){
        ProdutoEntradaGetDTO produtoEntradaGetDTO = produtoEntradaService.findById(id);

        ProdutoEntrada produtoEntrada = mapStructMapper.produtoEntradaToProdutoEntradaDTO(produtoEntradaPostDTO);
        produtoEntrada.setId(produtoEntradaGetDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body(produtoEntradaService.save(produtoEntrada));
    }

}


