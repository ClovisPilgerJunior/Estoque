package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.enums.Resuprimento;
import com.janfer.estoque.domain.entities.mappers.MapStructMapper;
import com.janfer.estoque.repositories.*;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoCapaService {

  @Autowired
  ProdutoCapaRepository produtoCapaRepository;

  @Autowired
  ProdutoEntradaRepository produtoEntradaRepository;

  @Autowired
  ProdutoSaidaRepository produtoSaidaRepository;

  @Autowired
  ProdutoPerdaRepository produtoPerdaRepository;

  @Autowired
  FornecedorRepository fornecedorRepository;

  @Autowired
  ProdutoEntradaService produtoEntradaService;

  @Autowired
  ProdutoPerdaService produtoPerdaService;

  @Autowired
  ProdutoSaidaService produtoSaidaService;

  @Autowired
  MapStructMapper mapStructMapper;


  @Transactional
  public List<ProdutoCapa> findAll() {
    return produtoCapaRepository.findAllAtivos();
  }

  @Transactional
  public ProdutoCapa save(ProdutoCapa produtoCapa) {

    if (produtoCapa.getFornecedor() != null && produtoCapa.getFornecedor().getId() != null) {
      Long fornecedorId = produtoCapa.getFornecedor().getId();
      if (!fornecedorRepository.existsById(fornecedorId)) {
        throw new ObjectNotFoundException("Fornecedor não encontrado");
      }
    }

    produtoCapa = produtoCapaRepository.save(produtoCapa);

    Long produtoCapaId = produtoCapa.getId();
    if (!produtoCapaRepository.isProdutoAtivoById(produtoCapaId)) {
      produtoCapa.setAtivo(false);
      produtoCapaRepository.save(produtoCapa);
    }

    return produtoCapa;
  }

  @Transactional
  public void delete(ProdutoCapa produtoCapa) {
    if (produtoEntradaRepository.existsById(produtoCapa.getId())) {
      throw new DataIntegrityViolationException("Não é possível excluir um produto com entrada existente");
    }
    produtoCapaRepository.delete(produtoCapa);
  }

  @Transactional
  public Optional<ProdutoCapa> findById(Long id) {
    return produtoCapaRepository.findById(id);
  }

  public boolean existById(Long id) {
    return produtoCapaRepository.existsById(id);
  }

  public boolean isProdutoAtivoById(Long id) {
    return produtoCapaRepository.isProdutoAtivoById(id);
  }

  public boolean existByDesc(String desc) {
    return produtoCapaRepository.existsByDesc(desc);
  }

  public List<ProdutoCapaGetDTO> obterProdutoCapaComCalculos(List<ProdutoCapa> produtoCapas) {
    List<ProdutoCapaGetDTO> produtoCapaGetDTOs = new ArrayList<>();

    for (ProdutoCapa produtoCapa : produtoCapas) {
      ProdutoCapaGetDTO produtoCapaGetDTO = mapStructMapper.produtoCapaToProdutoCapaGetDTO(produtoCapa);

      Long estoqueMinimo = produtoCapa.getMinimo();
      Long estoqueMaximo = produtoCapa.getMaximo();

      // Calculo de produtoEntrada

      Double somaEntradas = produtoEntradaService.calcularSomaEntradas(produtoCapa.getId());
      Double ultimoPrecoCompra = produtoEntradaService.recuperarUltimoPrecoCompra(produtoCapa.getId());

      // Calculo de produtoPerda
      Double somaPerdas = produtoPerdaService.calcularSomaPerdas(produtoCapa.getId());

      // Calculo do ProdutoSaida
      Double somaSaida = produtoSaidaService.calcularSomaSaida(produtoCapa.getId());


      produtoCapaGetDTO.setEntradas(somaEntradas != null ? somaEntradas : 0.0);
      produtoCapaGetDTO.setValorCompra(ultimoPrecoCompra != null ? ultimoPrecoCompra : 0.0);
      produtoCapaGetDTO.setPerdas(somaPerdas != null ? somaPerdas : 0.0);
      produtoCapaGetDTO.setSaidas(somaSaida != null ? somaSaida : 0.0);
      double saldo = ((somaEntradas != null ? somaEntradas : 0.0) - (somaPerdas != null ? somaPerdas : 0.0) - (somaSaida != null ? somaSaida : 0.0));

      double totalGeral = (saldo * (ultimoPrecoCompra != null ? ultimoPrecoCompra : 0.0));

      produtoCapaGetDTO.setSaldo(saldo);
      produtoCapaGetDTO.setValorTotal(totalGeral);

      Resuprimento resuprimento = calcularResuprimento(produtoCapaGetDTO.getSaldo(), produtoCapaGetDTO.getMinimo(), produtoCapaGetDTO.getMaximo());
      produtoCapaGetDTO.setResuprimento(resuprimento);

      produtoCapaGetDTOs.add(produtoCapaGetDTO);
    }

    return produtoCapaGetDTOs;
  }

  public Resuprimento calcularResuprimento(Double saldo, Long minimo, Long maximo) {
    if (saldo < 0) {
      return Resuprimento.ESTOQUE_NEGATIVO;
    } else if (saldo == 0) {
      return Resuprimento.SALDO_ZERADO;
    } else if (saldo > maximo) {
      return Resuprimento.PRODUTO_EXCESSO;
    } else if (saldo >= minimo && saldo <= maximo) {
      return Resuprimento.QUANTIDADE_IDEAL;
    } else {
      return Resuprimento.COMPRAR_AGORA;
    }

  }
}
