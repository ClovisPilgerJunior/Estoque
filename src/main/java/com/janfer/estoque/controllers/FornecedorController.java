package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.FornecedorService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.janfer.estoque.controllers.messages.FornecedorMessage.NOT_FOUND;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Fornecedores", description = "Endpoint de Fornecedores")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/fornecedor")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR'))")
public class FornecedorController {

  @Autowired
  MapStructMapper mapStructMapper;

  @Autowired
  FornecedorService fornecedorService;

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_FORNECEDOR_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR_CREATE'))")
  @PostMapping("/cadastrar")
  @Operation(summary = "Cadastrar um novo fornecedor", description = "Cadastra um novo fornecedor com base nos dados fornecidos.")
  @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso!")
  @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
  public ResponseEntity<FornecedorDTO> create(@Valid @RequestBody FornecedorDTO fornecedorDTO) {

    if (fornecedorService.existByEmpresa(fornecedorDTO.getEmpresa())) {
      throw new DataIntegrityViolationException("Empresa já cadastrada");
    }

    if (fornecedorService.existByEmail(fornecedorDTO.getEmail())) {
      throw new DataIntegrityViolationException("Email já cadastrado");
    }

    fornecedorService.save(mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fornecedorDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(fornecedorDTO);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_FORNECEDOR_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR_LIST'))")
  @GetMapping
  @Operation(summary = "Listar todos os fornecedores", description = "Recupera a lista de todos os fornecedores cadastrados.")
  @ApiResponse(responseCode = "200", description = "Lista de fornecedores encontrada com sucesso!")
  public ResponseEntity<List<FornecedorDTO>> getAll() {
    return new ResponseEntity<>(mapStructMapper.fornecedorAllToFornecedorDTO(fornecedorService.findAll()), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_FORNECEDOR_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR_LIST'))")
  @GetMapping("/{id}")
  @Operation(summary = "Buscar fornecedor por ID", description = "Recupera um fornecedor pelo ID fornecido.")
  @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso.")
  @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
    Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
    return fornecedorOptional.<ResponseEntity<Object>>map(fornecedor -> ResponseEntity
            .status(HttpStatus.OK)
            .body(fornecedor))
        .orElseGet(() -> ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(NOT_FOUND)
        );
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_FORNECEDOR_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR_UPDATE'))")
  @PutMapping("/atualizar/{id}")
  @Operation(summary = "Atualizar fornecedor por ID", description = "Atualiza um fornecedor pelo ID fornecido.")
  @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso.")
  @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
  @ApiResponse(responseCode = "400", description = "Violação na integridade dos dados")
  public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO) {
    Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
    if (fornecedorOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
    }

    Fornecedor fornecedor = mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO);
    fornecedor.setId(fornecedorOptional.get().getId());

    if (fornecedorService.existByEmpresaAndIdNot(fornecedorDTO.getEmpresa(), id)) {
      throw new DataIntegrityViolationException("Já existe um fornecedor com esse nome cadastrado");
    }

    if (fornecedorService.existByEmailAndIdNot(fornecedorDTO.getEmail(), id)) {
      throw new DataIntegrityViolationException("Email já cadastrado");
    }


    return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.save(fornecedor));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_FORNECEDOR_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_FORNECEDOR_DELETE'))")
  @DeleteMapping("/deletar/{id}")
  @Operation(summary = "Deletar fornecedor por ID", description = "Exclui um fornecedor pelo ID fornecido.")
  @ApiResponse(responseCode = "200", description = "Fornecedor excluído com sucesso!")
  @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @Positive @NotNull Long id) {
    fornecedorService.delete(id);
  }

}
