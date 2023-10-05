package com.janfer.estoque.domain.entities.mappers;

import com.janfer.estoque.domain.entities.*;
import com.janfer.estoque.domain.entities.dtos.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface MapStructMapper {

   MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

   // mapeamento do produtoCapa
   List<ProdutoCapaGetDTO> produtoCapaAllToProdutoCapaDTO(List<ProdutoCapa> produtoCapa);


   @Mapping(target = "tipoProduto", source = "tipoProduto")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade")
   @Mapping(target = "fornecedor", source = "fornecedor")
   @Mapping(target = "id", source = "id")
   @Mapping(target = "resuprimento", ignore = true)
   ProdutoCapa produtoCapaToProdutoCapaDTO(ProdutoCapaPostDTO produtoCapaDTO);

   ProdutoCapaGetDTO produtoCapaToProdutoCapaGetDTO(ProdutoCapa produtoCapa);

   // mapeamento do Fornecedor
   List<FornecedorDTO> fornecedorAllToFornecedorDTO(List<Fornecedor> fornecedor);

   @Mapping(target = "tipoEmpresa", source = "tipoEmpresa")
   Fornecedor fornecedorToFornecedorDTO(FornecedorDTO fornecedorDTO);

   @Mapping(target = "empresa", source = "tipoEmpresa")
   FornecedorGetDTO fornecedorGetDTOToFornecedor(Fornecedor fornecedor);

   // Mapeamento de ProdutoEntrada
   List<ProdutoEntradaGetDTO> produtoEntradaGetAllToProdutoEntrada(List<ProdutoEntrada> produtoEntradas);

   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaPostDTO produtoEntradaDTO);

   ProdutoEntradaGetDTO produtoEntradaToProdutoEntradaGetDTO(ProdutoEntrada produtoEntrada);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaGetDTO> produtoSaidaGetDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaPostDTO produtoSaidaDTO);

   ProdutoSaidaGetDTO produtoSaidaGetDTOToProdutoSaida(ProdutoSaida produtoSaida);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaGetDTO> produtoPerdaGetDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaPostDTO produtoPerdaDTO);

   ProdutoPerdaGetDTO produtoPerdaGetDTOToProdutoPerda(ProdutoPerda produtoPerda);
}
