package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.repositories.ProdutoSaidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoSaidaService {

    @Autowired
    ProdutoSaidaRepository produtoSaidaRepository;

    @Transactional
    public List<ProdutoSaida> findAll(){
        return produtoSaidaRepository.findAll();
    }

    public ProdutoSaida save(ProdutoSaida produtoSaida){
        return produtoSaidaRepository.save(produtoSaida);
    }

    @Transactional
    public void delete(ProdutoSaida produtoSaida){
        produtoSaidaRepository.delete(produtoSaida);
    }

    public Optional<ProdutoSaida> findById(Long id) {
        return produtoSaidaRepository.findById(id);
    }

    public Double calcularSomaSaida(Long produtoCapaId) {
        return produtoSaidaRepository.calcularSomaSaidas(produtoCapaId);
    }
}
