package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaDTO;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoCapaService {

  @Autowired
  ProdutoCapaRepository produtoCapaRepository;

  @Transactional
  public List<ProdutoCapa> findAll(){
    return produtoCapaRepository.findAll();
  }


}
