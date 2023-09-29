package com.janfer.estoque.domain.entities.mappers;

import com.janfer.estoque.domain.entities.*;
import com.janfer.estoque.domain.entities.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface MapStructMapper {

   MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

   // mapeamento do produtoCapa
   List<ProdutoCapaPostDTO> produtoCapaAllToProdutoCapaDTO(List<ProdutoCapa> produtoCapa);


   @Mapping(target = "tipoProduto", source = "tipoProduto")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade")
   @Mapping(target = "fornecedor", source = "fornecedor")
   ProdutoCapa produtoCapaDTOToProdutoCapa(ProdutoCapaPostDTO produtoCapaDTO);
//   ProdutoCapa produtoCapaToProdutoCapaDTO(ProdutoCapaDTO produtoCapaDTO);


   // mapeamento do Fornecedor
   List<FornecedorDTO> fornecedorAllToFornecedorDTO(List<Fornecedor> fornecedor);

   @Mapping(target = "tipoEmpresa", source = "tipoEmpresa")
   Fornecedor fornecedorTofornecedorDTO(FornecedorDTO fornecedorDTO);

//   default Integer tipoProduto(Set<TipoProduto> tipoProdutos) {
//      return tipoProdutos.size();
//   }
//
//   default Integer medidaUnidade(Set<MedidaUnidade> medidaUnidades) {
//      return medidaUnidades.size();
//   }
//
//   @Named("mapTipoEmpresaToEnum")
//   default TipoEmpresa mapTipoEmpresaToEnum(Integer tipoEmpresaCode) {
//      return TipoEmpresa.toEnum(tipoEmpresaCode);
//   }


   // Mapeamento de ProdutoEntrada
   List<ProdutoEntradaDTO> produtoEntradaAllToProdutoEntrada(List<ProdutoEntrada> produtoEntradas);
   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaDTO produtoEntradaDTO);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaDTO> produtoSaidaDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaDTO produtoSaidaDTO);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaDTO> produtoPerdaDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaDTO produtoPerdaDTO);
}
