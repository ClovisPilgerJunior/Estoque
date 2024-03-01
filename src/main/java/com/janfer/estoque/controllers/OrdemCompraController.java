package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.OrdemCompraDTO;
import com.janfer.estoque.domain.dtos.ItemOrdemProdutoDTO;
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
@RequestMapping(value = "/api/ordemCompra")
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
  public ResponseEntity<OrdemCompraDTO> createOrder(OrdemCompraDTO ordemCompra) {
    ordemCompra.setStatusOrdem(StatusOrdem.NAO_FATURADA);
    ordemCompraService.createOrder(mapStruct.toOrdemCompraDTO(ordemCompra));
    return ResponseEntity.ok(ordemCompra);
  }

  @PostMapping("/{orderId}/addProducts")
  public ResponseEntity<List<ItemOrdemProdutoDTO>> addProductsToOrder(
      @PathVariable Long orderId,
      @RequestBody List<ItemOrdemProdutoDTO> orderProductsDTO) {

    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    List<ItemOrdemProdutoDTO> itensAdicionados = new ArrayList<>();


    for (ItemOrdemProdutoDTO orderProductDTO : orderProductsDTO) {
      ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(orderProductDTO.getProdutoCapaId());
      ProdutoCapa produtoCapa = mapStruct.produtoCapaGetDTOToProdutoCapa(produtoCapaGetDTO);
      Long quantidade = orderProductDTO.getQuantidade();

      ItemOrdemCompra ordemProdutoDTO = ordemCompraService.addProductToOrder(ordemCompra, produtoCapa, quantidade);
      itensAdicionados.add(mapStruct.toItem(ordemProdutoDTO));
    }

    return new ResponseEntity<>(itensAdicionados, HttpStatus.CREATED);
  }

  @PutMapping("/{orderId}/faturar")
  public ResponseEntity<String> invoiceOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);

    if (ordemCompra.getItemOrdemCompras().isEmpty()) {
      return new ResponseEntity<>("A ordem de compra não pode ser faturada sem itens.", HttpStatus.BAD_REQUEST);
    }

    if (ordemCompra.getStatusOrdem() == StatusOrdem.toEnum(2)) {
      ordemCompraService.faturarOrdem(ordemCompra);
      return new ResponseEntity<>("Ordem faturada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A ordem já foi faturada.", HttpStatus.BAD_REQUEST);
    }
  }


  @PostMapping("/{orderId}/estornar")
  public ResponseEntity<String> cancelInvoiceOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);

    if (ordemCompra.getStatusOrdem() == StatusOrdem.toEnum(1)) {
      ordemCompraService.estornarOrdem(ordemCompra, orderId);
      return new ResponseEntity<>("Fatura estornada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A ordem não foi faturada.", HttpStatus.BAD_REQUEST);
    }
  }


  @GetMapping("/{orderId}/getOrderItems")
  public ResponseEntity<List<ItemOrdemCompra>> getOrderItems(@PathVariable Long orderId) {
    List<ItemOrdemCompra> itemOrdemCompras = ordemCompraService.getOrderById(orderId).getItemOrdemCompras();
    return new ResponseEntity<>(itemOrdemCompras, HttpStatus.OK);
  }
}

