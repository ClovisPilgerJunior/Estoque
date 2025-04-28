package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.ProdutoPerdaGetDTO;
import com.storage.stockflow.domain.entities.ProdutoPerda;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.ProdutoCapaRepository;
import com.storage.stockflow.repositories.ProdutoPerdaRepository;
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
public class ProdutoPerdaService {

    @Autowired
    ProdutoPerdaRepository produtoPerdaRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;

    @Autowired
    MapStructMapper mapStructMapper;

    @Transactional
    public List<ProdutoPerda> findAll(){
        return produtoPerdaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
