package com.janfer.estoque.domain.mappers;

import com.janfer.estoque.domain.dtos.*;
import com.janfer.estoque.domain.entities.*;
import com.janfer.estoque.domain.enums.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Set;

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

   @Mapping(target = "fornecedor", ignore = true)
   @Mapping(target = "tipoProduto", ignore = true)
   @Mapping(target = "medidaUnidade", ignore = true)
   @Mapping(target = "resuprimento", ignore = true)
   ProdutoCapa produtoCapaGetDTOToProdutoCapa(ProdutoCapaGetDTO produtoCapaGetDTO);


   @Mapping(target = "fornecedor.id", source = "fornecedor")
   @Mapping(target = "tipoProduto", source = "tipoProduto")
   @Mapping(target = "medidaUnidade", source = "medidaUnidade", qualifiedByName = "mapMedidaUnidade")
   @Mapping(target = "resuprimento", ignore = true)
   @Mapping(target = "id", ignore = true)
   ProdutoCapa produtoCapaToProdutoCapaPostDTO(ProdutoCapaPostDTO produtoCapaPostDTO);


   default TipoProduto mapTipoProduto(Integer tipoProdutoCod) {
      return TipoProduto.toEnum(tipoProdutoCod);
   }

   @Named("mapMedidaUnidade")
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

   List<ProdutoEntradaPostDTO> toProdutoEntrada(List<ProdutoEntrada> produtoEntradas);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   ProdutoEntrada produtoEntradaToProdutoEntradaDTO(ProdutoEntradaPostDTO produtoEntradaDTO);

   @Mapping(target = "produtoCapa", source = "produtoCapa.id")
   @Mapping(target = "produtoCapaDesc", source = "produtoCapa.description")
   @Default
   ProdutoEntradaGetDTO produtoEntradaGetDTOToProdutoEntrada(ProdutoEntrada produtoEntrada);

   @Mapping(source = "produtoCapa.id", target = "produtoCapa")
   ProdutoEntradaPostDTO produtoEntradaPostDTOToProdutoEntrada(ProdutoEntrada produtoEntrada);

   // Mapeamento de ProdutoSaida
   List<ProdutoSaidaGetDTO> produtoSaidaGetDTOAllToProdutoSaida(List<ProdutoSaida> produtoSaidas);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   @Mapping(source = "unidadeProdutiva", target = "unidadeProdutiva.id")
   ProdutoSaida produtoSaidaToProdutoSaidaDTO(ProdutoSaidaPostDTO produtoSaidaDTO);

   @Mapping(source = "produtoCapa.id", target = "produtoCapa")
   @Mapping(source = "setor.cod", target = "setor")
   @Mapping(source = "unidadeProdutiva.id", target = "unidadeProdutiva")
   ProdutoSaidaPostDTO produtoSaidaPostDTOToProdutoSaida(ProdutoSaida produtoSaida);

   default Setor mapSetor(Integer setorCod) {
      return Setor.toEnum(setorCod);
   }

   @Mapping(target = "produtoCapa", source = "produtoCapa.id")
   @Mapping(target = "produtoCapaDesc", source = "produtoCapa.description")
   @Mapping(target = "setor", source = "setor.desc")
   @Mapping(target = "unidadeProdutiva", source = "unidadeProdutiva.nome")
   @Mapping(target = "servico", source = "unidadeProdutiva.servico")
   @Default
   ProdutoSaidaGetDTO produtoSaidaGetDTOToProdutoSaida(ProdutoSaida produtoSaida);

   // Mapeamento de ProdutoPerda

   List<ProdutoPerdaGetDTO> produtoPerdaGetDTOAllToProdutoPerda(List<ProdutoPerda> produtoPerdas);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "produtoCapa.id", source = "produtoCapa")
   ProdutoPerda produtoPerdaToProdutoPerdaDTO(ProdutoPerdaPostDTO produtoPerdaDTO);

   @Mapping(target = "produtoCapa", source = "produtoCapa.id")
   ProdutoPerdaPostDTO produtoPerdaPostDTOtoProdutoPerda(ProdutoPerda produtoPerda);

   @Mapping(target = "produtoCapa", source = "produtoCapa.id")
   @Mapping(target = "produtoCapaDesc", source = "produtoCapa.description")
   @Default
   ProdutoPerdaGetDTO produtoPerdaGetDTOToProdutoPerda(ProdutoPerda produtoPerda);

   List<UnidadeProdutivaGetDTO> unidadeProdutivaGetAllDTOToUnidadeProdutiva(List<UnidadeProdutiva> unidadeProdutiva);

   UnidadeProdutivaGetDTO unidadeProdutivaGetDTOToUnidadeProdutiva(UnidadeProdutiva unidadeProdutiva);


   UnidadeProdutivaPostDTO unidadeProdutivaPostDTOToUnidadeProdutiva(UnidadeProdutivaPostDTO unidadeProdutiva);
   @Mapping(target = "id", ignore = true)
   UnidadeProdutiva unidadeProdutivaUnidadeProdutivaPostDTO(UnidadeProdutivaPostDTO unidadeProdutivaPostDTO);


   @Mapping(target = "id", ignore = true)
   User userToUserPostDTO(UserPostDTO userPostDTO);

   List<UserGetDTO> userListAllUser(List<User> userGetDTOS);

   UserGetDTO userGetDTOToUser(User user);

   Set<Integer> profileToIntegerSet(Set<Profile> profiles);

   Set<Profile> profileToIntegerSetP(Set<Integer> profiles);

   default Integer mapProfileToInteger(Profile profile) {
      if (profile == null) {
         return null;
      }
      return profile.getCode();
   }

   default Profile mapIntegerToProfile(Integer code) {
      if (code == null) {
         return null;
      }
      return Profile.toEnum(code);
   }

   @Mapping(target = "produtoCapa.id", source = "ordemCompra.id")
   @Mapping(target = "id", source = "ordemCompra.id")
  ProdutoEntrada itemOrdemCompraToProdutoEntrada(ItemOrdemCompra item);

   @Mapping(source = "produtoCapa.id", target = "produtoCapaId")
   @Mapping(source = "ordemCompra.id", target = "ordemCompraId")
   ItemOrdemProdutoDTO toItem(ItemOrdemCompra itemOrdemCompra);

   @Mapping(target = "produtoCapa.id", source = "produtoCapaId")
   @Mapping(target = "ordemCompra.id", source = "ordemCompraId")
   ItemOrdemCompra toOrder(ItemOrdemProdutoDTO itemOrdemProdutoDTO);

   default StatusOrdem mapStatusOrdem(Integer statusOrdemCod) {
      return StatusOrdem.toEnum(statusOrdemCod);
   }

   @Mapping(source = "fornecedor", target = "fornecedor.id")
   OrdemCompra toOrdemCompraToPostDTO(OrdemCompraPostDTO ordemCompraPostDTO);

   @Mapping(source = "statusOrdem.cod", target = "statusOrdem")
   @Mapping(source = "fornecedor.id", target = "fornecedor")
   OrdemCompraPostDTO toOrdemCompraPostEntity(OrdemCompra ordemCompra);

   @Mapping(source = "statusOrdem.desc", target = "statusOrdem")
   @Mapping(source = "fornecedor.empresa", target = "fornecedor")
   OrdemCompraGetDTO toOrdemCompraGetEntity(OrdemCompra ordemCompra);

   List<OrdemCompraGetDTO> toOrdemCompraList(List<OrdemCompra> ordemCompras);

   @Mapping(source = "produtoCapa.id", target = "produtoCapaId")
   @Mapping(source = "ordemCompra.id", target = "ordemCompraId")
   @Mapping(source = "produtoCapa.description", target = "produtoCapaDesc")
   ItemOrdemProdutoGetDTO toItemOrdemCompraEntity(ItemOrdemCompra itemOrdemCompra);

   List<ItemOrdemProdutoGetDTO> toItemOrdemCompraList(List<ItemOrdemCompra> itemOrdemCompra);


   @Mapping(source = "fornecedor.id", target = "fornecedorId")
   OrdemCompraUpdateDTO ordemCompraPutDTOToEntity(OrdemCompra ordemCompra);
}
