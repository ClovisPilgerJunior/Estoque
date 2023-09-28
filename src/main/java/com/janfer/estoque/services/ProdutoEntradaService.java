package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoEntradaService {

    @Autowired
    ProdutoEntradaRepository produtoEntradaRepository;

    @Transactional
    public List<ProdutoEntrada> findAll(){
        return produtoEntradaRepository.findAll();
    }

    public ProdutoEntrada save(ProdutoEntrada produtoEntrada){
        return produtoEntradaRepository.save(produtoEntrada);
    }

    @Transactional
    public void delete(ProdutoEntrada produtoEntrada){
        produtoEntradaRepository.delete(produtoEntrada);
    }

    public Optional<ProdutoEntrada> findById(Long id) {
        return produtoEntradaRepository.findById(id);
    }
}
