package com.janfer.estoque.services;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

  public void createOrder(OrdemCompra ordemCompra) {
      orderRepository.save(ordemCompra);
  }

  public ItemOrdemCompra addProductToOrder(OrdemCompra ordemCompra, ProdutoCapa produtoCapa, Long quantidade) {
   ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
    itemOrdemCompra.setOrdemCompra(ordemCompra);
    itemOrdemCompra.setProdutoCapa(produtoCapa);
    itemOrdemCompra.setQuantidade(quantidade);

    return orderItemRepository.save(itemOrdemCompra);
  }


    public void faturarOrdem(OrdemCompra ordemCompra) {
        for (ItemOrdemCompra item : ordemCompra.getItemOrdemCompras()) {
            ProdutoEntrada produtoEntrada = new ProdutoEntrada();
            produtoEntrada.setProdutoCapa(item.getProdutoCapa());
            produtoEntrada.setOrdemCompra(item.getOrdemCompra());
            produtoEntrada.setQuantidade(item.getQuantidade());
            // Configure outros campos conforme necessário
            produtoEntradaRepository.save(produtoEntrada);
        }

        // Atualize o status da ordem de compra
        ordemCompra.setStatusOrdem(StatusOrdem.FATURADA);
        orderRepository.save(ordemCompra);
    }

  public void estornarOrdem(OrdemCompra ordemCompra) {
    ordemCompra.setStatusOrdem(StatusOrdem.toEnum(2));
  }


}

