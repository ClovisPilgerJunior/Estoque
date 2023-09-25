package com.janfer.estoque.domain.entities.mappers;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import com.janfer.estoque.domain.entities.enums.MedidaUnidade;
import com.janfer.estoque.domain.entities.enums.TipoEmpresa;
import com.janfer.estoque.domain.entities.enums.TipoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "Spring")
public interface MapStructMapper {

   @Mapping(target = "tipoProduto", source = "produtoCapaDTO")
   @Mapping(target = "medidaUnidade", source = "produtoCapa")
   List<ProdutoCapaDTO> produtoCapaAllToProdutoCapaDTO(List<ProdutoCapa> produtoCapa);

   List<FornecedorDTO> fornecedorAllToFornecedorDTO(List<Fornecedor> fornecedor);

   @Mapping(target = "tipoEmpresa", expression = "java(mapToTipoEmpresa(tipoEmpresaDTO.getTipoEmpresa()))")
   Fornecedor fornecedorTofornecedorDTO(FornecedorDTO fornecedorDTO);

   default Integer tipoProduto(Set<TipoProduto> tipoProdutos) {
      return tipoProdutos.size();
   }

   default Integer medidaUnidade(Set<MedidaUnidade> medidaUnidades) {
      return medidaUnidades.size();
   }

   default TipoEmpresa mapToTipoEmpresa(Integer tipoEmpresa) {
      return tipoEmpresa != null ? TipoEmpresa.toEnum(tipoEmpresa) : null;
   }

}
