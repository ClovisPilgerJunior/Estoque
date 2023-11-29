package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.ProdutoCapaCalculatedGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoEntradaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoSaidaGetDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoSaidaRepository;
import com.janfer.estoque.repositories.UnidadeProdutivaRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import com.janfer.estoque.services.exceptions.SaldoNegativoException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return produtoSaidaRepository.findAll();
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
