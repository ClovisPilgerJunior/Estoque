package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.repositories.ProdutoPerdaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoPerdaService {

    @Autowired
    ProdutoPerdaRepository produtoPerdaRepository;

    @Transactional
    public List<ProdutoPerda> findAll(){
        return produtoPerdaRepository.findAll();
    }

    public ProdutoPerda save(ProdutoPerda produtoPerda){
        return produtoPerdaRepository.save(produtoPerda);
    }

    @Transactional
    public void delete(ProdutoPerda produtoPerda){
        produtoPerdaRepository.delete(produtoPerda);
    }

    public Optional<ProdutoPerda> findById(Long id) {
        return produtoPerdaRepository.findById(id);
    }

    public Double calcularSomaPerdas(Long produtoCapaId) {
        return produtoPerdaRepository.calcularSomaPerdas(produtoCapaId);
    }
}
