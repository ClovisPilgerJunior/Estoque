package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.repositories.ItemOrdemCompraRepository;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemCompraService {
  @Autowired
  private OrdemCompraRepository orderRepository;

  @Autowired
  private ItemOrdemCompraRepository orderItemRepository;

  public OrdemCompra getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ObjectNotFoundException("Order not found with id: " + orderId));
  }

  public OrdemCompra createOrder() {
    OrdemCompra ordemCompra = new OrdemCompra();
    return orderRepository.save(ordemCompra);
  }

  public ItemOrdemCompra addProductToOrder(OrdemCompra ordemCompra, ProdutoCapa produtoCapa, int quantity) {
    ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
    itemOrdemCompra.setOrdemCompra(ordemCompra);
    itemOrdemCompra.setProdutoCapa(produtoCapa); // Aqui assumindo que você tem um método toProduct em ProdutoCapa
    itemOrdemCompra.setQuantity(quantity);
    return orderItemRepository.save(itemOrdemCompra);
  }


  public List<ItemOrdemCompra> getOrderItems(OrdemCompra ordemCompra) {
    return ordemCompra.getItemOrdemCompras();
  }
}

