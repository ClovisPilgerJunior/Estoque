package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.ProdutoCapaCalculatedGetDTO;
import com.storage.stockflow.domain.dtos.ProdutoCapaGetDTO;
import com.storage.stockflow.domain.entities.ProdutoCapa;
import com.storage.stockflow.domain.enums.Resuprimento;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.FornecedorRepository;
import com.storage.stockflow.repositories.ProdutoCapaRepository;
import com.storage.stockflow.repositories.ProdutoEntradaRepository;
import com.storage.stockflow.services.exceptions.DataIntegrityViolationException;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoCapaService {

  @Autowired
  ProdutoCapaRepository produtoCapaRepository;

  @Autowired
  ProdutoEntradaRepository produtoEntradaRepository;

  @Autowired
  FornecedorRepository fornecedorRepository;

  @Autowired
  ProdutoEntradaService produtoEntradaService;

  @Autowired
  ProdutoPerdaService produtoPerdaService;

  private ProdutoSaidaService produtoSaidaService;

  @Autowired
  public void setProdutoSaidaService(ProdutoSaidaService produtoSaidaService) {
    this.produtoSaidaService = produtoSaidaService;
  }

  @Autowired
  MapStructMapper mapStructMapper;


  @Transactional
  public List<ProdutoCapa> findAll() {
    return produtoCapaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  @Transactional
  public ProdutoCapa save(ProdutoCapa produtoCapa) {

    if (produtoCapa.getFornecedor() != null && produtoCapa.getFornecedor().getId() != null) {
      Long fornecedorId = produtoCapa.getFornecedor().getId();
      if (!fornecedorRepository.existsById(fornecedorId)) {
        throw new ObjectNotFoundException("Fornecedor não encontrado");
      }
    }

    if(produtoCapa.getResuprimento() == null){
      produtoCapa.setResuprimento(Resuprimento.SALDO_ZERADO);
    }

    // Salve o produtoCapa
    produtoCapaRepository.save(produtoCapa);

    Long produtoCapaId = produtoCapa.getId();
    if (Boolean.FALSE.equals(produtoCapaRepository.isProdutoAtivoById(produtoCapaId))) {
      produtoCapa.setAtivo(false);
      produtoCapaRepository.save(produtoCapa);
    }

    return produtoCapa;
  }


  @Transactional
  public void delete(@Positive @NotNull Long id) {
    if (produtoEntradaRepository.existsById(id)) {
      throw new DataIntegrityViolationException("Não é possível excluir um produto com entrada existente");
    }
    produtoCapaRepository.delete(produtoCapaRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("produto capa não Encontrado!")));
  }


  public ProdutoCapaGetDTO findById(Long id) {
    return produtoCapaRepository.findById(id).map(mapStructMapper::produtoCapaGetDTOToProdutoCapa)
            .orElseThrow(() -> new ObjectNotFoundException("Produto entrada não encontrado"));
  }

  public boolean existById(Long id) {
    return produtoCapaRepository.existsById(id);
  }

  public boolean isProdutoAtivoById(Long id) {
    return produtoCapaRepository.isProdutoAtivoById(id);
  }

  public List<ProdutoCapaCalculatedGetDTO> obterProdutoCapaComCalculos(List<ProdutoCapa> produtoCapas) {
    List<ProdutoCapaCalculatedGetDTO> produtoCapaGetDTOs = new ArrayList<>();

    for (ProdutoCapa produtoCapa : produtoCapas) {
      if(produtoCapaRepository.isProdutoAtivoById(produtoCapa.getId())) {
        ProdutoCapaCalculatedGetDTO produtoCapaCalculatedGetDTO = calcularProdutoCapa(produtoCapa);

        produtoCapaGetDTOs.add(produtoCapaCalculatedGetDTO);
      }
    }

    return produtoCapaGetDTOs;
  }

  private ProdutoCapaCalculatedGetDTO calcularProdutoCapa(ProdutoCapa produtoCapa)  {

        ProdutoCapaCalculatedGetDTO produtoCapaCalculatedGetDTO = mapStructMapper.produtoCapaToProdutoCapaCalculatedGetDTO(produtoCapa);

        // Calculo de produtoEntrada

        Double somaEntradas = produtoEntradaService.calcularSomaEntradas(produtoCapa.getId());
        Double ultimoPrecoCompra = produtoEntradaService.recuperarUltimoPrecoCompra(produtoCapa.getId());

        // Calculo de produtoPerda
        Double somaPerdas = produtoPerdaService.calcularSomaPerdas(produtoCapa.getId());

        // Calculo do ProdutoSaida
        Double somaSaida = produtoSaidaService.calcularSomaSaida(produtoCapa.getId());


        produtoCapaCalculatedGetDTO.setEntradas(somaEntradas != null ? somaEntradas : 0.0);
        produtoCapaCalculatedGetDTO.setValorCompra(ultimoPrecoCompra != null ? ultimoPrecoCompra : 0.0);
        produtoCapaCalculatedGetDTO.setPerdas(somaPerdas != null ? somaPerdas : 0.0);
        produtoCapaCalculatedGetDTO.setSaidas(somaSaida != null ? somaSaida : 0.0);
        double saldo = ((somaEntradas != null ? somaEntradas : 0.0) - (somaPerdas != null ? somaPerdas : 0.0) - (somaSaida != null ? somaSaida : 0.0));

        double totalGeral = (saldo * (ultimoPrecoCompra != null ? ultimoPrecoCompra : 0.0));

        produtoCapaCalculatedGetDTO.setSaldo(saldo);
        produtoCapaCalculatedGetDTO.setValorTotal(totalGeral);

        long minimo = produtoCapaCalculatedGetDTO.getMinimo() != null ? produtoCapaCalculatedGetDTO.getMinimo() : 0L;
        long maximo = produtoCapaCalculatedGetDTO.getMaximo() != null ? produtoCapaCalculatedGetDTO.getMaximo() : 0L;

        String resuprimento = calcularResuprimento(produtoCapaCalculatedGetDTO.getSaldo(), minimo, maximo);

        produtoCapaCalculatedGetDTO.setResuprimento(resuprimento);


    return produtoCapaCalculatedGetDTO;
  }

  public String calcularResuprimento(Double saldo, Long minimo, Long maximo) {

    if (saldo < 0) {
      return "ESTOQUE NEGATIVO";
    } else if (saldo == 0) {
      return "SEM SALDO";
    } else if (saldo < minimo) {
      return "COMPRAR AGORA";
    } else if (saldo >= minimo && saldo <= maximo) {
      return "QUANTIDADE IDEAL";
    } else {
      return "PRODUTO EM EXCESSO";
    }

  }

  public boolean existByDesc(String desc){
    return produtoCapaRepository.existsByDesc(desc);
  }

  public boolean existByDescAndIdNot(String desc, Long id){
    return produtoCapaRepository.existsByDescAndIdNot(desc, id);
  }

}
