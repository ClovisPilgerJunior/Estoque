package com.janfer.estoque.domain.entities.mapper;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface MapStructMapper {

   List<ProdutoCapaDTO> produtoCapaToProdutocapaDTO(List<ProdutoCapa> produtoCapa);

}
