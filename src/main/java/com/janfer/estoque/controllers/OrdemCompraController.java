package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.OrdemCompraGetDTO;
import com.janfer.estoque.domain.dtos.ItemOrdemProdutoDTO;
import com.janfer.estoque.domain.dtos.OrdemCompraPostDTO;
import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.services.OrdemCompraService;
import com.janfer.estoque.services.ProdutoCapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
  @Autowired
  private OrdemCompraRepository ordemCompraRepository;

    @PostMapping
    public ResponseEntity<OrdemCompraPostDTO> createOrder(@RequestBody OrdemCompraPostDTO ordemCompraPostDTO) {
        OrdemCompra ordemCompra = mapStruct.toOrdemCompraToPostDTO(ordemCompraPostDTO);
        OrdemCompra savedOrder = ordemCompraService.generateOrder(ordemCompra);
        // Atualiza o DTO de entrada com o ID gerado para a nova ordem de compra
        ordemCompraPostDTO.setId(savedOrder.getId());
        return ResponseEntity.ok(ordemCompraPostDTO);
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
      Double precoCompra = orderProductDTO.getPrecoCompra();
      Double valorTotaItemOrdem = orderProductDTO.getQuantidade() * orderProductDTO.getPrecoCompra();

      ItemOrdemCompra ordemProdutoDTO = ordemCompraService.addProductToOrder(ordemCompra,
              produtoCapa,
              quantidade,
              precoCompra,
              valorTotaItemOrdem);
      itensAdicionados.add(mapStruct.toItem(ordemProdutoDTO));
    }

    ordemCompra.getQuantidadeItens();
    ordemCompraRepository.save(ordemCompra);

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

  @GetMapping
  public ResponseEntity<List<OrdemCompraGetDTO>> getAllOrders() {
    List<OrdemCompra> ordens = ordemCompraService.getAllOrdem();
    List<OrdemCompraGetDTO> ordensDTO = new ArrayList<>();

    for (OrdemCompra ordemCompra : ordens) {
      OrdemCompraGetDTO ordemCompraGetDTO = mapStruct.toOrdemCompraGetEntity(ordemCompra);

      // Calcula a quantidade de itens e o valor total
      int quantidadeItens = ordemCompra.getQuantidadeItens();
      Double valorTotal = ordemCompra.calcularValorTotal();

      // Adiciona os valores ao DTO
      ordemCompraGetDTO.setQuantidade(quantidadeItens);
      ordemCompraGetDTO.setValorTotal(valorTotal);

      ordensDTO.add(ordemCompraGetDTO);
    }

    return ResponseEntity.ok(ordensDTO);
  }

  @GetMapping("/{orderId}/getOrderItems")
  public ResponseEntity<List<ItemOrdemCompra>> getOrderItems(@PathVariable Long orderId) {
    List<ItemOrdemCompra> itemOrdemCompras = ordemCompraService.getOrderById(orderId).getItemOrdemCompras();
    return new ResponseEntity<>(itemOrdemCompras, HttpStatus.OK);
  }
}

