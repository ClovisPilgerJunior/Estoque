package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoEntradaService {

    @Autowired
    ProdutoEntradaRepository produtoEntradaRepository;
    @Autowired
    private ProdutoCapaRepository produtoCapaRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Transactional
    public List<ProdutoEntrada> findAll(){
        return produtoEntradaRepository.findAll();
    }

    public ProdutoEntrada save(ProdutoEntrada produtoEntrada){

        if(Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoEntrada.getProdutoCapa().getId()))){
            throw new ProductDisableException("Produto está inativo");
        }

        return produtoEntradaRepository.save(produtoEntrada);
    }

    @Transactional
    public void delete(@Positive @NotNull Long id){

        produtoEntradaRepository.delete(produtoEntradaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não Encontrado!")));

    }

    public Optional<ProdutoEntrada> findById(Long id) {
        return produtoEntradaRepository.findById(id);
    }

    public Double calcularSomaEntradas(Long produtoCapaId) {
        return produtoEntradaRepository.calcularSomaEntradas(produtoCapaId);
    }

    public Double recuperarUltimoPrecoCompra(Long produtoCapaId){
        return produtoEntradaRepository.recuperarUltimoPrecoCompra(produtoCapaId);
    }

}
