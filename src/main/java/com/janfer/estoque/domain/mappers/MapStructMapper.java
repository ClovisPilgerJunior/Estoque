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
   @Mapping(source = "medidaUnidade.desc", target = "medidaUnidade")
   @Mapping(source = "resuprimento.desc", target = "resuprimento")
   @Default
   ProdutoCapaGetDTO produtoCapaGetDTOToProdutoCapa(ProdutoCapa produtoCapa);




   @Mapping(target = "fornecedor.id", source = "fornecedor")
   @Mapping(target = "tipoProduto", source = "tipoProduto")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade")
   @Mapping(target = "resuprimento", ignore = true)
   @Mapping(target = "id", ignore = true)
   ProdutoCapa produtoCapaToProdutoCapaPostDTO(ProdutoCapaPostDTO produtoCapaPostDTO);


   default TipoProduto mapTipoProduto(Integer tipoProdutoCod) {
      return TipoProduto.toEnum(tipoProdutoCod);
   }

   default MedidaUnidade mapMedidaUnidade(Integer medidaUnidadeCod) {
      return MedidaUnidade.toEnum(medidaUnidadeCod);
   }

   @Mapping(source = "fornecedor.id", target = "fornecedor")
   @Mapping(source = "tipoProduto.cod", target = "tipoProduto")
   @Mapping(source = "medidaUnidade.cod", target = "medidaUnidade" )
   ProdutoCapaPostDTO produtoCapaPostDTOToProdutoCapa(ProdutoCapa produtoCapa);


   @Mapping(target = "entradas", ignore = true)
   @Mapping(target = "saidas", ignore = true)
   @Mapping(target = "perdas", ignore = true)
   @Mapping(target = "saldo", ignore = true)
   @Mapping(target = "valorCompra", ignore = true)
   @Mapping(target = "valorTotal", ignore = true)
   @Mapping(target = "fornecedor", source = "fornecedor.empresa")
   @Mapping(target = "tipoProduto", source = "tipoProduto.desc")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade.desc")
   @Mapping(target = "resuprimento", source = "resuprimento.desc")
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
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaPostDTO produtoEntradaDTO);

   @Mapping(target = "produtoCapa", source = "produtoCapa.description")
   ProdutoEntradaGetDTO produtoEntradaToProdutoEntradaGetDTO(ProdutoEntrada produtoEntrada);

   @Mapping(source = "produtoCapa.id", target = "produtoCapa")
   ProdutoEntradaPostDTO produtoEntradaPostDTOToProdutoEntrar(ProdutoEntrada produtoEntrada);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaGetDTO> produtoSaidaGetDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaPostDTO produtoSaidaDTO);

   @Mapping(source = "produtoCapa.id", target = "produtoCapa")
   ProdutoSaidaPostDTO produtoSaidaPostDTOToProdutoSaida(ProdutoSaida produtoSaida);

   @Mapping(target = "produtoCapa", source = "produtoCapa.description")
   ProdutoSaidaGetDTO produtoSaidaGetDTOToProdutoSaida(ProdutoSaida produtoSaida);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaGetDTO> produtoPerdaGetDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaPostDTO produtoPerdaDTO);

   @Mapping(target = "produtoCapa", source = "produtoCapa.id")
   ProdutoPerdaPostDTO produtoPerdaPostDTOtoProdutoPerda(ProdutoPerda produtoPerda);

   @Mapping(target = "produtoCapa", source = "produtoCapa.description")
   ProdutoPerdaGetDTO produtoPerdaGetDTOToProdutoPerda(ProdutoPerda produtoPerda);
}
