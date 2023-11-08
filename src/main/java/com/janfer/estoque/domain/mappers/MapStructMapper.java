package com.janfer.estoque.domain.mappers;

import com.janfer.estoque.domain.dtos.*;
import com.janfer.estoque.domain.entities.*;
import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.TipoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.enterprise.inject.Default;
import java.util.List;

@Mapper(componentModel = "Spring")
public interface MapStructMapper {

   MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

   // mapeamento do produtoCapa
   List<ProdutoCapaGetDTO> produtoCapaAllToProdutoCapaDTO(List<ProdutoCapa> produtoCapa);

   @Mapping(source = "fornecedor.empresa", target = "fornecedor")
   @Mapping(source = "tipoProduto.desc", target = "tipoProduto")
   @Mapping(source = "resuprimento.desc", target = "resuprimento")
   @Default
   ProdutoCapaGetDTO produtoCapaGetDTOToProdutoCapa(ProdutoCapa produtoCapa);




   @Mapping(source = "fornecedor", target = "fornecedor.id")
   @Mapping(target = "tipoProduto", source = "tipoProduto")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade")
   @Mapping(target = "resuprimento", ignore = true)
   ProdutoCapa produtoCapaToProdutoCapaDTO(ProdutoCapaPostDTO produtoCapaPostDTO);


   default TipoProduto mapTipoProduto(Integer tipoProdutoCod) {
      return TipoProduto.toEnum(tipoProdutoCod);
   }

   default MedidaUnidade mapMedidaUnidade(Integer medidaUnidadeCod) {
      return MedidaUnidade.toEnum(medidaUnidadeCod);
   }

   @Mapping(source = "fornecedor.id", target = "fornecedor")
   @Mapping(source = "tipoProduto.cod", target = "tipoProduto")
   @Mapping(source = "medidaUnidade.cod", target = "medidaUnidade" )
   ProdutoCapaPostDTO produtoCapaDTOToProdutoCapa(ProdutoCapa produtoCapa);


   @Mapping(target = "entradas", ignore = true)
   @Mapping(target = "saidas", ignore = true)
   @Mapping(target = "perdas", ignore = true)
   @Mapping(target = "saldo", ignore = true)
   @Mapping(target = "valorCompra", ignore = true)
   @Mapping(target = "valorTotal", ignore = true)
   ProdutoCapaCalculatedGetDTO produtoCapaToProdutoCapaCalculatedGetDTO(ProdutoCapa produtoCapa);

   // mapeamento do Fornecedor
   List<FornecedorDTO> fornecedorAllToFornecedorDTO(List<Fornecedor> fornecedor);

   @Mapping(target = "tipoEmpresa", source = "tipoEmpresa")
   Fornecedor fornecedorToFornecedorDTO(FornecedorDTO fornecedorDTO);

   @Mapping(target = "id", source = "id")
   @Mapping(target = "empresa", source = "empresa")
   FornecedorGetDTO fornecedorGetDTOToFornecedor(Fornecedor fornecedor);

   // Mapeamento de ProdutoEntrada
   List<ProdutoEntradaGetDTO> produtoEntradaGetAllToProdutoEntrada(List<ProdutoEntrada> produtoEntradas);

   @Mapping(target = "id", ignore = true)
   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaPostDTO produtoEntradaDTO);

   ProdutoEntradaGetDTO produtoEntradaToProdutoEntradaGetDTO(ProdutoEntrada produtoEntrada);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaGetDTO> produtoSaidaGetDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   @Mapping(target = "id", ignore = true)
   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaPostDTO produtoSaidaDTO);

   ProdutoSaidaGetDTO produtoSaidaGetDTOToProdutoSaida(ProdutoSaida produtoSaida);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaGetDTO> produtoPerdaGetDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   @Mapping(target = "id", ignore = true)
   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaPostDTO produtoPerdaDTO);

   ProdutoPerdaGetDTO produtoPerdaGetDTOToProdutoPerda(ProdutoPerda produtoPerda);
}
