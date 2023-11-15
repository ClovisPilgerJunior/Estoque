package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoSaidaRepository;
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
public class ProdutoSaidaService {

    @Autowired
    ProdutoSaidaRepository produtoSaidaRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;

    @Transactional
    public List<ProdutoSaida> findAll(){
        return produtoSaidaRepository.findAll();
    }

    public ProdutoSaida save(ProdutoSaida produtoSaida){

        if(Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoSaida.getProdutoCapa().getId()))){
            throw new ProductDisableException("Produto está inativo");
        }

        return produtoSaidaRepository.save(produtoSaida);
    }

    @Transactional
    public void delete(@Positive @NotNull Long id){

        produtoSaidaRepository.delete(produtoSaidaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não Encontrado!")));

    }

    public Optional<ProdutoSaida> findById(Long id) {
        return produtoSaidaRepository.findById(id);
    }

    public Double calcularSomaSaida(Long produtoCapaId) {
        return produtoSaidaRepository.calcularSomaSaidas(produtoCapaId);
    }
}
