package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.ProdutoCapaCalculatedGetDTO;
import com.storage.stockflow.domain.dtos.ProdutoSaidaGetDTO;
import com.storage.stockflow.domain.entities.ProdutoSaida;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.ProdutoCapaRepository;
import com.storage.stockflow.repositories.ProdutoSaidaRepository;
import com.storage.stockflow.repositories.UnidadeProdutivaRepository;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import com.storage.stockflow.services.exceptions.ProductDisableException;
import com.storage.stockflow.services.exceptions.SaldoNegativoException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProdutoSaidaService {

    @Autowired
    ProdutoSaidaRepository produtoSaidaRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;

    @Autowired
    UnidadeProdutivaRepository unidadeProdutivaRepository;

    private ProdutoCapaService produtoCapaService;

    @Autowired
    MapStructMapper mapStructMapper;

    @Autowired
    public void setProdutoCapaService(@Lazy ProdutoCapaService produtoCapaService) {
        this.produtoCapaService = produtoCapaService;
    }

    @Transactional
    public List<ProdutoSaida> findAll(){
        return produtoSaidaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public ProdutoSaida save(ProdutoSaida produtoSaida) {
        // Verificar se o produto está ativo
        if(Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoSaida.getProdutoCapa().getId()))){
            throw new ProductDisableException("Produto está inativo");
        }

        if(produtoSaida.getUnidadeProdutiva().getId() == null) {
            produtoSaida.setUnidadeProdutiva(null);
        }

        if (produtoSaida.getUnidadeProdutiva() != null && produtoSaida.getUnidadeProdutiva().getId() != null) {
            if (!unidadeProdutivaRepository.existsById(produtoSaida.getUnidadeProdutiva().getId())) {
                throw new ObjectNotFoundException("Unidade produtiva não encontrada!");
            }
        }

        if (produtoSaida.getUnidadeProdutiva() != null) {
            if (!unidadeProdutivaRepository.isProdutoAtivoById(produtoSaida.getUnidadeProdutiva().getId())) {
                throw new ProductDisableException("Unidade produtiva está desativada");
            }
        }

        // Obter as informações calculadas relevantes para a saída
        List<ProdutoCapaCalculatedGetDTO> produtoCapaCalculados = produtoCapaService.obterProdutoCapaComCalculos(
            Collections.singletonList(produtoSaida.getProdutoCapa()));

        if (!produtoCapaCalculados.isEmpty()) {
            ProdutoCapaCalculatedGetDTO produtoCapaCalculado = produtoCapaCalculados.get(0);

            // Verificar se a quantidade de saída é maior que o saldo disponível
            if (produtoSaida.getQuantidade() > produtoCapaCalculado.getSaldo()) {
                throw new SaldoNegativoException("A quantidade de saída é maior que o saldo disponível.\rSaldo Disponivel: " + produtoCapaCalculado.getSaldo());
            }

            return produtoSaidaRepository.save(produtoSaida);

        } else {
            throw new ObjectNotFoundException("Produto capa não encontrado");
        }
    }


    @Transactional
    public void delete(@Positive @NotNull Long id){

        produtoSaidaRepository.delete(produtoSaidaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não Encontrado!")));

    }

    public ProdutoSaidaGetDTO findById(Long id) {
        return produtoSaidaRepository.findById(id).map(mapStructMapper::produtoSaidaGetDTOToProdutoSaida)
                .orElseThrow(() -> new ObjectNotFoundException("Produto capa não encontrado"));
    }

    public Double calcularSomaSaida(Long produtoCapaId) {
        return produtoSaidaRepository.calcularSomaSaidas(produtoCapaId);
    }
}
