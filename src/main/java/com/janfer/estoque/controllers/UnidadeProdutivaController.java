package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.UnidadeProdutivaGetDTO;
import com.janfer.estoque.domain.dtos.UnidadeProdutivaPostDTO;
import com.janfer.estoque.domain.entities.UnidadeProdutiva;
import com.janfer.estoque.services.UnidadeProdutivaService;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "UnidadeProdutiva", description = "Endpoint da Unidade Produtiva")
@Server(url = "http://localhost:8080", description = "Servidor local de desenvolvimento")
@Server(url = "http://estoque-production.up.railway.app", description = "Servidor de produção")
@RestController
@RequestMapping(value = "/api/unidadeProdutiva")
@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_VIEW'))")
public class UnidadeProdutivaController {

  @Autowired
  UnidadeProdutivaService unidadeProdutivaService;

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_UNIDADEPRODUTIVA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_LIST'))")
  @GetMapping
  public List<UnidadeProdutivaGetDTO> listAll(){
    return unidadeProdutivaService.listAll();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_UNIDADEPRODUTIVA_LIST')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_LIST'))")
  @GetMapping("/{id}")
  public UnidadeProdutivaGetDTO findById(@PathVariable @Positive @NotNull Long id){
    return unidadeProdutivaService.findById(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_UNIDADEPRODUTIVA_CREATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_CREATE'))")
  @PostMapping("/cadastrar")
  public UnidadeProdutiva save(@Valid @RequestBody UnidadeProdutivaPostDTO unidadeProdutivaPostDTO) {
    return unidadeProdutivaService.save(unidadeProdutivaPostDTO);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_UNIDADEPRODUTIVA_UPDATE')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_UPDATE'))")
  @PutMapping("/atualizar/{id}")
  public UnidadeProdutiva update(@Valid @RequestBody UnidadeProdutivaPostDTO unidadeProdutivaPostDTO
      , @PathVariable @Positive @NotNull Long id){
    return unidadeProdutivaService.update(unidadeProdutivaPostDTO, id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasRole('ROLE_UNIDADEPRODUTIVA_DELETE')) or (hasRole('ROLE_USER') and hasRole('ROLE_UNIDADEPRODUTIVA_DELETE'))")
  @DeleteMapping("/deletar/{id}")
  public void deletar(@PathVariable @Positive @NotNull Long id){
    unidadeProdutivaService.delete(id);
  }
}
