package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.OrdemProdutoDTO;
import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.OrdemCompraService;
import com.janfer.estoque.services.ProdutoCapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrdemCompraController {
  @Autowired
  private OrdemCompraService ordemCompraService;

  @Autowired
  private ProdutoCapaService produtoCapaService;

  @Autowired
  MapStructMapper mapStruct;

  @PostMapping
  public ResponseEntity<com.janfer.estoque.domain.entities.OrdemCompra> createOrder() {
    OrdemCompra ordemCompra = ordemCompraService.createOrder();
    return new ResponseEntity<>(ordemCompra, HttpStatus.CREATED);
  }

  @PostMapping("/{orderId}/addProduct")
  public ResponseEntity<ItemOrdemCompra> addProductToOrder(
      @PathVariable Long orderId,
      @RequestBody OrdemProdutoDTO orderProductDTO) {

    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(orderProductDTO.getProdutoCapaId());

    // Convertendo ProdutoCapaGetDTO para ProdutoCapa
    ProdutoCapa produtoCapa = mapStruct.produtoCapaGetDTOToProdutoCapa(produtoCapaGetDTO);

    int quantity = orderProductDTO.getQuantidade();

    ItemOrdemCompra itemOrdemCompra = ordemCompraService.addProductToOrder(ordemCompra, produtoCapa, quantity);
    return new ResponseEntity<>(itemOrdemCompra, HttpStatus.CREATED);
  }

  @GetMapping("/{orderId}/getOrderItems")
  public ResponseEntity<List<ItemOrdemCompra>> getOrderItems(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    List<ItemOrdemCompra> itemOrdemCompras = ordemCompraService.getOrderItems(ordemCompra);
    return new ResponseEntity<>(itemOrdemCompras, HttpStatus.OK);
  }
}

