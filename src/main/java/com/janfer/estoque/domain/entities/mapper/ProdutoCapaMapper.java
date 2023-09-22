package com.janfer.estoque.domain.entities.mapper;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoCapaMapper {

   ProdutoCapa toEntity(ProdutoCapaDTO teste);

}
