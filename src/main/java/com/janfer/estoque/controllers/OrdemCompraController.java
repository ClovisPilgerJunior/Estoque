package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.OrdemProdutoDTO;
import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.OrdemCompraService;
import com.janfer.estoque.services.ProdutoCapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrdemCompraController {

  @Autowired
  private AuthenticationManager authenticationManager;
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

  @PostMapping("/{orderId}/addProducts")
  public ResponseEntity<List<OrdemProdutoDTO>> addProductsToOrder(
      @PathVariable Long orderId,
      @RequestBody List<OrdemProdutoDTO> orderProductsDTO) {

    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    List<OrdemProdutoDTO> itensAdicionados = new ArrayList<>();


    for (OrdemProdutoDTO orderProductDTO : orderProductsDTO) {
      ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(orderProductDTO.getProdutoCapaId());
      ProdutoCapa produtoCapa = mapStruct.produtoCapaGetDTOToProdutoCapa(produtoCapaGetDTO);
      int quantidade = orderProductDTO.getQuantidade();

      ItemOrdemCompra ordemProdutoDTO = ordemCompraService.addProductToOrder(ordemCompra, produtoCapa, quantidade);
      itensAdicionados.add(mapStruct.toItem(ordemProdutoDTO));
    }

    return new ResponseEntity<>(itensAdicionados, HttpStatus.CREATED);
  }

  @PostMapping("/{orderId}/faturar")
  public ResponseEntity<String> invoiceOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);

    if (ordemCompra.getStatusOrdem() == StatusOrdem.toEnum(2)) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      // Supondo que o Authentication seja um UsernamePasswordAuthenticationToken
      String accessToken = (String) authentication.getCredentials();
      ordemCompraService.faturarOrdem(ordemCompra, accessToken);
      return new ResponseEntity<>("Ordem faturada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A ordem já foi faturada.", HttpStatus.BAD_REQUEST);
    }
  }


  @PostMapping("/{orderId}/estornar")
  public ResponseEntity<String> cancelInvoiceOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);

    if (ordemCompra.getStatusOrdem() == StatusOrdem.toEnum(1)) {
      ordemCompraService.estornarOrdem(ordemCompra);
      return new ResponseEntity<>("Fatura cancelada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A ordem não foi faturada.", HttpStatus.BAD_REQUEST);
    }
  }


  @GetMapping("/{orderId}/getOrderItems")
  public ResponseEntity<List<ItemOrdemCompra>> getOrderItems(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    List<ItemOrdemCompra> itemOrdemCompras = ordemCompraService.getOrderItems(ordemCompra);
    return new ResponseEntity<>(itemOrdemCompras, HttpStatus.OK);
  }
}

