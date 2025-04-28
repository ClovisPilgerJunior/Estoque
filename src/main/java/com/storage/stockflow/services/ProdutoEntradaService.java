package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.ProdutoEntradaGetDTO;
import com.storage.stockflow.domain.entities.ProdutoEntrada;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.FornecedorRepository;
import com.storage.stockflow.repositories.ProdutoCapaRepository;
import com.storage.stockflow.repositories.ProdutoEntradaRepository;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import com.storage.stockflow.services.exceptions.ProductDisableException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoEntradaService {

    @Autowired
    ProdutoEntradaRepository produtoEntradaRepository;
    @Autowired
    private ProdutoCapaRepository produtoCapaRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    MapStructMapper mapStructMapper;

    @Transactional
    public List<ProdutoEntrada> findAll(){
        return produtoEntradaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public ProdutoEntrada save(ProdutoEntrada produtoEntrada){

        if(Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoEntrada.getProdutoCapa().getId()))){
            throw new ProductDisableException("Produto está inativo");
        }

        return produtoEntradaRepository.save(produtoEntrada);
    }

    public void saveAll(List<ProdutoEntrada> produtoEntrada) {

        produtoEntradaRepository.saveAll(produtoEntrada);
    }

    @Transactional
    public void delete(@Positive @NotNull Long id){

        produtoEntradaRepository.delete(produtoEntradaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não Encontrado!")));

    }

    public ProdutoEntradaGetDTO findById(Long id) {
        return produtoEntradaRepository.findById(id).map(mapStructMapper::produtoEntradaGetDTOToProdutoEntrada)
            .orElseThrow(() -> new ObjectNotFoundException("Produto capa não encontrado"));
    }

    public Double calcularSomaEntradas(Long produtoCapaId) {
        return produtoEntradaRepository.calcularSomaEntradas(produtoCapaId);
    }

    public Double recuperarUltimoPrecoCompra(Long produtoCapaId){
        return produtoEntradaRepository.recuperarUltimoPrecoCompra(produtoCapaId);
    }
}
