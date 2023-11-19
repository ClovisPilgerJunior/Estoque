package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.ProdutoPerdaGetDTO;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoPerdaRepository;
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
public class ProdutoPerdaService {

    @Autowired
    ProdutoPerdaRepository produtoPerdaRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;

    @Autowired
    MapStructMapper mapStructMapper;

    @Transactional
    public List<ProdutoPerda> findAll(){
        return produtoPerdaRepository.findAll();
    }

    public ProdutoPerda save(ProdutoPerda produtoPerda){

        if(Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoPerda.getProdutoCapa().getId()))){
            throw new ProductDisableException("Produto está inativo");
        }

        return produtoPerdaRepository.save(produtoPerda);
    }

    @Transactional
    public void delete(@Positive @NotNull Long id){

        produtoPerdaRepository.delete(produtoPerdaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não Encontrado!")));

    }
    public ProdutoPerdaGetDTO findById(Long id) {
        return produtoPerdaRepository.findById(id).map(mapStructMapper::produtoPerdaGetDTOToProdutoPerda)
                .orElseThrow(() -> new ObjectNotFoundException("Produto capa não encontrado"));
    }

    public Double calcularSomaPerdas(Long produtoCapaId) {
        return produtoPerdaRepository.calcularSomaPerdas(produtoCapaId);
    }
}
