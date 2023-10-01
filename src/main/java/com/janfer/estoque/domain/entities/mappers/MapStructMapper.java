package com.janfer.estoque.domain.entities.mappers;

import com.janfer.estoque.domain.entities.*;
import com.janfer.estoque.domain.entities.dtos.*;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
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
   ProdutoCapa produtoCapaToProdutoCapaDTO(ProdutoCapaPostDTO produtoCapaDTO);

   ProdutoCapaGetDTO produtoCapaToProdutoCapaGetDTO(ProdutoCapa produtoCapa);

   // mapeamento do Fornecedor
   List<FornecedorDTO> fornecedorAllToFornecedorDTO(List<Fornecedor> fornecedor);

   @Mapping(target = "tipoEmpresa", source = "tipoEmpresa")
   Fornecedor fornecedorToFornecedorDTO(FornecedorDTO fornecedorDTO);

   @Mapping(target = "empresa", source = "tipoEmpresa")
   FornecedorGetDTO fornecedorGetDTOToFornecedor(Fornecedor fornecedor);

   // Mapeamento de ProdutoEntrada
   List<ProdutoEntradaDTO> produtoEntradaAllToProdutoEntrada(List<ProdutoEntrada> produtoEntradas);

   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaDTO produtoEntradaDTO);


   @Mapping(target = "id", source = "id")
   ProdutoEntradaDTO produtoEntradaToProdutoEntradaDTO(ProdutoEntrada produtoEntrada);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaDTO> produtoSaidaDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaDTO produtoSaidaDTO);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaDTO> produtoPerdaDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaDTO produtoPerdaDTO);
}
