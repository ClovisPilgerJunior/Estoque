package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoCapaService {

  @Autowired
  ProdutoCapaRepository produtoCapaRepository;

  @Transactional
  public List<ProdutoCapa> findAll(){
    return produtoCapaRepository.findAll();
  }


  public ProdutoCapa save(ProdutoCapa produtoCapa){
    return produtoCapaRepository.save(produtoCapa);
  }

  @Transactional
  public void delete(ProdutoCapa produtoCapa){
    produtoCapaRepository.delete(produtoCapa);
  }

  public Optional<ProdutoCapa> findById(Long id) {
    return produtoCapaRepository.findById(id);
  }
}
