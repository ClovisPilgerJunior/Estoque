package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.UnidadeProdutivaGetDTO;
import com.janfer.estoque.domain.dtos.UnidadeProdutivaPostDTO;
import com.janfer.estoque.domain.entities.UnidadeProdutiva;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ProdutoSaidaRepository;
import com.janfer.estoque.repositories.UnidadeProdutivaRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeProdutivaService {

  @Autowired
  UnidadeProdutivaRepository unidadeProdutivaRepository;

  @Autowired
  MapStructMapper mapStructMapper;
  @Autowired
  private ProdutoSaidaRepository produtoSaidaRepository;

  public List<UnidadeProdutivaGetDTO> listAll() {
    return mapStructMapper.unidadeProdutivaGetAllDTOToUnidadeProdutiva(unidadeProdutivaRepository.findAll());
  }

  public UnidadeProdutivaGetDTO findById(Long id){
    return unidadeProdutivaRepository.findById(id).map(mapStructMapper::unidadeProdutivaGetDTOToUnidadeProdutiva)
        .orElseThrow(() -> new ObjectNotFoundException("Unidade produtiva não encontrada!"));
  }

  public UnidadeProdutiva save(UnidadeProdutivaPostDTO unidadeProdutivaPostDTO){

    if(unidadeProdutivaRepository.existsByNome(unidadeProdutivaPostDTO.getNome())){
      throw new DataIntegrityViolationException("Unidade produtiva já cadastrada!");
    }


    return unidadeProdutivaRepository.save(mapStructMapper.unidadeProdutivaUnidadeProdutivaPostDTO(unidadeProdutivaPostDTO));
  }

  public UnidadeProdutiva update(UnidadeProdutivaPostDTO unidadeProdutivaPostDTO, Long id){
    UnidadeProdutivaGetDTO unidadeProdutivaGetDTO = unidadeProdutivaRepository.findById(id)
        .map(mapStructMapper::unidadeProdutivaGetDTOToUnidadeProdutiva)
        .orElseThrow(()-> new ObjectNotFoundException("Unidade produtiva não encontrada!"));

    if(unidadeProdutivaRepository.existsByNome(unidadeProdutivaPostDTO.getNome())
    && !unidadeProdutivaRepository.existsById(id)) {
      throw new DataIntegrityViolationException("Unidade produtiva já cadastrada");
    }



    UnidadeProdutiva unidadeProdutiva = mapStructMapper.unidadeProdutivaUnidadeProdutivaPostDTO(unidadeProdutivaPostDTO);
    unidadeProdutiva.setId(unidadeProdutivaGetDTO.getId());
    return unidadeProdutivaRepository.save(unidadeProdutiva);
  }

  public void delete(@Positive @NotNull Long id){
    if(produtoSaidaRepository.existsById(id)){
      throw new DataIntegrityViolationException("Não é possível excluir uma unidade produtiva com saídas existentes");
    }
    unidadeProdutivaRepository.delete(unidadeProdutivaRepository.findById(id)
        .orElseThrow(()-> new ObjectNotFoundException("Unidade produtiva não encontrada!")));
  }

}
