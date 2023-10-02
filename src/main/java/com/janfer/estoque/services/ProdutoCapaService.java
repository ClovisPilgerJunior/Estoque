package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.repositories.*;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoCapaService {

  @Autowired
  private ProdutoCapaRepository produtoCapaRepository;

  @Autowired
  private ProdutoEntradaRepository produtoEntradaRepository;

  @Autowired
  private ProdutoSaidaRepository produtoSaidaRepository;

  @Autowired
  private ProdutoPerdaRepository produtoPerdaRepository;

  @Autowired
  private FornecedorRepository fornecedorRepository;

  @Transactional
  public List<ProdutoCapa> findAll(){
    return produtoCapaRepository.findAll();
  }


  public ProdutoCapa save(ProdutoCapa produtoCapa){

    if (produtoCapa.getFornecedor() != null && produtoCapa.getFornecedor().getId() != null) {
      Long fornecedorId = produtoCapa.getFornecedor().getId();
      if (!fornecedorRepository.existsById(fornecedorId)) {
        throw new ObjectNotFoundException("Fornecedor não encontrado");
      }
    }

    return produtoCapaRepository.save(produtoCapa);
  }

  @Transactional
  public void delete(ProdutoCapa produtoCapa){
    if(produtoEntradaRepository.existsById(produtoCapa.getId())){
      throw new DataIntegrityViolationException("Não é possível excluir um produto com entrada existente");
    }
    produtoCapaRepository.delete(produtoCapa);
  }

  public Optional<ProdutoCapa> findById(Long id) {
    return produtoCapaRepository.findById(id);
  }

  public boolean existById(Long id){
    return produtoCapaRepository.existsById(id);
  }

  public boolean existByDesc(String desc){
    return produtoCapaRepository.existsByDesc(desc);
  }

}
