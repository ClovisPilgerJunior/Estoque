package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.UnidadeProdutivaGetDTO;
import com.storage.stockflow.domain.dtos.UnidadeProdutivaPostDTO;
import com.storage.stockflow.domain.entities.UnidadeProdutiva;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.ProdutoSaidaRepository;
import com.storage.stockflow.repositories.UnidadeProdutivaRepository;
import com.storage.stockflow.services.exceptions.DataIntegrityViolationException;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    return mapStructMapper.unidadeProdutivaGetAllDTOToUnidadeProdutiva(unidadeProdutivaRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
  }

  public UnidadeProdutivaGetDTO findById(Long id){
    return unidadeProdutivaRepository.findById(id).map(mapStructMapper::unidadeProdutivaGetDTOToUnidadeProdutiva)
        .orElseThrow(() -> new ObjectNotFoundException("Unidade produtiva não encontrada!"));
  }

  public UnidadeProdutiva save(UnidadeProdutivaPostDTO unidadeProdutivaPostDTO){

    if(unidadeProdutivaRepository.existsByNome(unidadeProdutivaPostDTO.getNome())){
      throw new DataIntegrityViolationException("Unidade produtiva já cadastrada!");
    }

    unidadeProdutivaPostDTO.setAtivo(true);

    return unidadeProdutivaRepository.save(mapStructMapper.unidadeProdutivaUnidadeProdutivaPostDTO(unidadeProdutivaPostDTO));
  }

  public UnidadeProdutiva update(UnidadeProdutivaPostDTO unidadeProdutivaPostDTO, Long id){
    if (unidadeProdutivaRepository.existsByNomeAndIdNot(unidadeProdutivaPostDTO.getNome(), id)) {
      throw new DataIntegrityViolationException("Já existe uma unidade produtiva com esse nome.");
    }

    UnidadeProdutivaGetDTO unidadeProdutivaGetDTO = unidadeProdutivaRepository.findById(id)
        .map(mapStructMapper::unidadeProdutivaGetDTOToUnidadeProdutiva)
        .orElseThrow(() -> new ObjectNotFoundException("Unidade produtiva não encontrada!"));

    // Atualiza os dados da unidade produtiva com base no DTO recebido
    UnidadeProdutiva unidadeProdutiva = mapStructMapper.unidadeProdutivaUnidadeProdutivaPostDTO(unidadeProdutivaPostDTO);
    unidadeProdutiva.setId(unidadeProdutivaGetDTO.getId());

    // Salva a unidade produtiva atualizada
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
