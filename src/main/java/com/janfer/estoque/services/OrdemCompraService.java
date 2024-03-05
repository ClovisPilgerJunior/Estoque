package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.OrdemCompraDTO;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ItemOrdemCompraRepository;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrdemCompraService {

  @Autowired
  private OrdemCompraRepository orderRepository;

  @Autowired
  private ItemOrdemCompraRepository orderItemRepository;

  @Autowired
  private ProdutoEntradaRepository produtoEntradaRepository;

  @Autowired
  private MapStructMapper mapStructMapper;


  public OrdemCompra getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ObjectNotFoundException("Order not found with id: " + orderId));
  }

  public List<OrdemCompra> getAllOrdem() {
      return orderRepository.findAll();
  }

  public void createOrder(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.NAO_FATURADA);
      ordemCompra.setDataPedidoOrdemCompra(new Date());
      orderRepository.save(ordemCompra);
  }

  public ItemOrdemCompra addProductToOrder(OrdemCompra ordemCompra, ProdutoCapa produtoCapa, Long quantidade, Double precoCompra) {
   ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
    itemOrdemCompra.setOrdemCompra(ordemCompra);
    itemOrdemCompra.setProdutoCapa(produtoCapa);
    itemOrdemCompra.setQuantidade(quantidade);
    itemOrdemCompra.setPrecoCompra(precoCompra);

    return orderItemRepository.save(itemOrdemCompra);
  }


    public void faturarOrdem(OrdemCompra ordemCompra) {
        for (ItemOrdemCompra item : ordemCompra.getItemOrdemCompras()) {
            ProdutoEntrada produtoEntrada = new ProdutoEntrada();
            produtoEntrada.setProdutoCapa(item.getProdutoCapa());
            produtoEntrada.setOrdemCompra(item.getOrdemCompra());
            produtoEntrada.setQuantidade(item.getQuantidade());
            produtoEntrada.setPrecoCompra(item.getPrecoCompra());
            produtoEntrada.setDataPedido(new Date());
            // Configure outros campos conforme necessário
            produtoEntradaRepository.save(produtoEntrada);
        }

        // Atualize o status da ordem de compra
        ordemCompra.setStatusOrdem(StatusOrdem.FATURADA);
        ordemCompra.setDataPedidoOrdemCompra(new Date());
        ordemCompra.setValorTotal(ordemCompra.getValorTotal());
        orderRepository.save(ordemCompra);
    }

    public void estornarOrdem(OrdemCompra ordemCompra ,Long orderId) {
        ordemCompra.setStatusOrdem(StatusOrdem.NAO_FATURADA);
        orderRepository.save(ordemCompra);
        // Obter a lista de ProdutoEntrada associadas à ordem de compra
        List<ProdutoEntrada> produtoEntradas = produtoEntradaRepository.findByOrdemCompraId(orderId);
        // Deletar todas as ProdutoEntrada na lista de uma vez
        produtoEntradaRepository.deleteAll(produtoEntradas);
    }


}

