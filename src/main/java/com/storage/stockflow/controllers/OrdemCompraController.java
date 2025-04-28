package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.*;
import com.storage.stockflow.domain.entities.Fornecedor;
import com.storage.stockflow.domain.entities.ItemOrdemCompra;
import com.storage.stockflow.domain.entities.OrdemCompra;
import com.storage.stockflow.domain.entities.ProdutoCapa;
import com.storage.stockflow.domain.enums.StatusOrdem;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.OrdemCompraRepository;
import com.storage.stockflow.services.FornecedorService;
import com.storage.stockflow.services.OrdemCompraService;
import com.storage.stockflow.services.ProdutoCapaService;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/ordemCompra")
public class OrdemCompraController {

  @Autowired
  private OrdemCompraService ordemCompraService;

  @Autowired
  private ProdutoCapaService produtoCapaService;

  @Autowired
  MapStructMapper mapStruct;
  @Autowired
  private OrdemCompraRepository ordemCompraRepository;

  @Autowired
  private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<OrdemCompraPostDTO> createOrder(@RequestBody OrdemCompraPostDTO ordemCompraPostDTO) {
        OrdemCompra ordemCompra = mapStruct.toOrdemCompraToPostDTO(ordemCompraPostDTO);
        ordemCompra.setNumeroNotaOrdem(ordemCompraPostDTO.getNumeroNotaOrdem());
        ordemCompra.setOrdemObservacao(ordemCompraPostDTO.getOrdemObservacao());
        ordemCompra.setNomeSolicitante(ordemCompraPostDTO.getNomeSolicitante());
        ordemCompra.setDataPrevisaoEntrega(ordemCompraPostDTO.getDataPrevisaoEntrega() == null ? null : ordemCompraPostDTO.getDataPrevisaoEntrega() );
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
      String produtoCapaDesc = orderProductDTO.getProdutoCapaDesc();
      Double quantidade = orderProductDTO.getQuantidade();
      Double precoCompra = orderProductDTO.getPrecoCompra();
      Long numeroNota = orderProductDTO.getNumeroNota();
      String observacao = orderProductDTO.getObservacao();
      Double valorTotaItemOrdem = orderProductDTO.getQuantidade() * orderProductDTO.getPrecoCompra();


      ItemOrdemCompra ordemProdutoDTO = ordemCompraService.addProductToOrder(ordemCompra,
              produtoCapa,
              quantidade,
              precoCompra,
              valorTotaItemOrdem,
              numeroNota,
              observacao,
              produtoCapaDesc);
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

    if (ordemCompra.getStatusOrdem().equals(StatusOrdem.AGUARDANDO_RECEBIMENTO)) {
      ordemCompraService.faturarOrdem(ordemCompra);
      return new ResponseEntity<>("Ordem de Compra recebida com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A ordem de Compra já foi recebida.", HttpStatus.BAD_REQUEST);
    }
  }


  @PostMapping("/{orderId}/estornar")
  public ResponseEntity<String> cancelInvoiceOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);

    if (ordemCompra.getStatusOrdem().equals(StatusOrdem.RECEBIDO)) {
      ordemCompraService.estornarOrdem(ordemCompra, orderId);
      return new ResponseEntity<>("Ordem de compra estornada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("A Ordem de Compra não foi Recebida.", HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/{orderId}/liberar")
  public ResponseEntity<String> releasePurchaseOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ordemCompraService.liberarOrdem(ordemCompra);
    return new ResponseEntity<>("Ordem liberada com sucesso!.", HttpStatus.OK);
  }

  @PutMapping("/{orderId}/devolver")
  public ResponseEntity<String> returnPurchaseOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ordemCompraService.devolverOrdem(ordemCompra);
    return new ResponseEntity<>("Ordem devolvida para revisão!.", HttpStatus.OK);
  }

  @PutMapping("/{orderId}/realizarPedido")
  public ResponseEntity<String> placePurchaseOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ordemCompraService.relizarPedidoOrdem(ordemCompra);
    return new ResponseEntity<>("Pedido realizado com sucesso!.", HttpStatus.OK);
  }

  @PutMapping("/{orderId}/cancelar")
  public ResponseEntity<String> cancelPurchaseOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ordemCompraService.cancelarOrdem(ordemCompra);
    return new ResponseEntity<>("Ordem de compra cancelada!.", HttpStatus.OK);
  }

  @PutMapping("/{orderId}/revisar")
  public ResponseEntity<String> reviewPurchaseOrder(@PathVariable Long orderId) {
    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    ordemCompraService.revisarOrdem(ordemCompra);
    return new ResponseEntity<>("Ordem Revisada com sucesso!.", HttpStatus.OK);
  }

  @PutMapping("/{id}/atualizar-data-previsao")
  public ResponseEntity<?> atualizarDataPrevisao(@PathVariable Long id, @RequestBody OrdemCompra ordemCompra) {
    try {
      OrdemCompra updatedOrder = ordemCompraService.updateDataPrevisao(id, ordemCompra.getDataPrevisaoEntrega());
      return ResponseEntity.ok(updatedOrder);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao atualizar a data de previsão de entrega");
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
  public ResponseEntity<List<ItemOrdemProdutoGetDTO>> getOrderItems(@PathVariable Long orderId) {
    List<ItemOrdemProdutoGetDTO> itemOrdemProdutoGetDTOS = mapStruct.toItemOrdemCompraList(ordemCompraService.getOrderById(orderId).getItemOrdemCompras());
    return new ResponseEntity<>(itemOrdemProdutoGetDTOS, HttpStatus.OK);
  }

  @PutMapping("/{orderId}/updateOrder")
  public ResponseEntity<List<ItemOrdemProdutoDTO>> updateOrder(
          @PathVariable Long orderId,
          @RequestBody OrdemCompraUpdateDTO updateDTO) {

    OrdemCompra ordemCompra = ordemCompraService.getOrderById(orderId);
    List<ItemOrdemProdutoDTO> updatedItems = new ArrayList<>();

    // Atualiza o fornecedor da ordem de compra
    if (updateDTO.getFornecedorId() != null) {
      Fornecedor fornecedor = fornecedorService.findById(updateDTO.getFornecedorId())
              .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado com id: " + updateDTO.getFornecedorId()));
      ordemCompra.setFornecedor(fornecedor);
    }

    ordemCompra.setNumeroNotaOrdem(updateDTO.getNumeroNotaOrdem());
    ordemCompra.setOrdemObservacao(updateDTO.getOrdemObservacao());
    ordemCompra.setNomeSolicitante(updateDTO.getNomeSolicitante());
    ordemCompra.setDataPrevisaoEntrega(updateDTO.getDataPrevisaoEntrega());


    // Primeiro, identifica os IDs dos itens que foram removidos da lista enviada
    Set<Long> removedItemIds = new HashSet<>(ordemCompra.getItemOrdemCompras().stream()
            .map(ItemOrdemCompra::getProdutoCapa)
            .map(ProdutoCapa::getId)
            .collect(Collectors.toSet()));
    updateDTO.getItems().forEach(itemDTO -> removedItemIds.remove(itemDTO.getProdutoCapaId()));

    // Exclui os itens que foram removidos da lista enviada
    removedItemIds.forEach(id -> ordemCompraService.deleteItemFromOrder(ordemCompra, id));

    // Agora, atualiza ou adiciona os itens enviados na lista
    for (ItemOrdemProdutoDTO itemDTO : updateDTO.getItems()) {
      ProdutoCapaGetDTO produtoCapaGetDTO = produtoCapaService.findById(itemDTO.getProdutoCapaId());
      ProdutoCapa produtoCapa = mapStruct.produtoCapaGetDTOToProdutoCapa(produtoCapaGetDTO);
      Double quantidade = itemDTO.getQuantidade();
      Double precoCompra = itemDTO.getPrecoCompra();
      Long numeroNota = itemDTO.getNumeroNota();
      String observacao = itemDTO.getObservacao();
      String produtoCapaDesc = itemDTO.getProdutoCapaDesc();
      Double valorTotalItemOrdem = itemDTO.getQuantidade() * itemDTO.getPrecoCompra();

      // Verifica se o item já existe na ordem de compra
      Optional<ItemOrdemCompra> existingItem = ordemCompra.getItemOrdemCompras().stream()
              .filter(item -> item.getProdutoCapa().getId().equals(produtoCapa.getId()))
              .findFirst();

      if (existingItem.isPresent()) {
        // Atualiza o item existente
        ItemOrdemCompra item = existingItem.get();
        item.setQuantidade(quantidade);
        item.setPrecoCompra(precoCompra);
        item.setValorTotalOrdem(valorTotalItemOrdem);
        item.setObservacao(observacao);
        item.setNumeroNota(numeroNota);
        ordemCompraService.updateItem(item);
      } else {
        // Adiciona um novo item
        ItemOrdemCompra newItem = ordemCompraService.addProductToOrder(ordemCompra,
                produtoCapa,
                quantidade,
                precoCompra,
                valorTotalItemOrdem,
                numeroNota,
                observacao,
                produtoCapaDesc);
        updatedItems.add(mapStruct.toItem(newItem));
      }
    }

    ordemCompraRepository.save(ordemCompra);

    return new ResponseEntity<>(updatedItems, HttpStatus.OK);
  }




}

