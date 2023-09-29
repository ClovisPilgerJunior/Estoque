package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.repositories.*;
import com.janfer.estoque.services.exceptions.FornecedorNaoEncontradoException;
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
        throw new FornecedorNaoEncontradoException("Fornecedor não encontrado");
      }
    }

    return produtoCapaRepository.save(produtoCapa);
  }

  @Transactional
  public void delete(ProdutoCapa produtoCapa){
    produtoCapaRepository.delete(produtoCapa);
  }

  public Optional<ProdutoCapa> findById(Long id) {
    return produtoCapaRepository.findById(id);
  }

//  public ProdutoCapaGetDTO consultarEstoquePorId(Long idProduto) {
//    ProdutoCapa produtoCapa = produtoCapaRepository.findById(idProduto)
//        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
//
//    Integer quantidadeEntrada = produtoEntradaRepository.sumQuantidadeByIdProduto(idProduto);
//
//    // Consultar o fornecedor do produto
////    Fornecedor fornecedor = fornecedorRepository.findById(produtoCapa.getFornecedor().getId())
////        .orElse(null);
//
//    ProdutoCapaGetDTO produtoCapaGetDTO = new ProdutoCapaGetDTO();
//    produtoCapaGetDTO.setSku(produtoCapa.getId());
//    produtoCapaGetDTO.setDescription(produtoCapa.getDescription());
//    produtoCapaGetDTO.setQuantidadeEntrada(quantidadeEntrada != null ? quantidadeEntrada : 0);
//
//    return produtoCapaGetDTO;
//  }
  
}
