package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.*;
import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.services.FornecedorService;
import com.janfer.estoque.services.OrdemCompraService;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

  @Autowired
  private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<OrdemCompraPostDTO> createOrder(@RequestBody OrdemCompraPostDTO ordemCompraPostDTO) {
        OrdemCompra ordemCompra = mapStruct.toOrdemCompraToPostDTO(ordemCompraPostDTO);
        ordemCompra.setNumeroNotaOrdem(ordemCompraPostDTO.getNumeroNotaOrdem());
        ordemCompra.setOrdemObservacao(ordemCompraPostDTO.getOrdemObservacao());
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
      Long numeroNota = orderProductDTO.getNumeroNota();
      String observacao = orderProductDTO.getObservacao();
      Double valorTotaItemOrdem = orderProductDTO.getQuantidade() * orderProductDTO.getPrecoCompra();


      ItemOrdemCompra ordemProdutoDTO = ordemCompraService.addProductToOrder(ordemCompra,
              produtoCapa,
              quantidade,
              precoCompra,
              valorTotaItemOrdem,
              numeroNota,
              observacao);
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

      if (updateDTO.getNumeroNotaOrdem() != null) {
          ordemCompra.setNumeroNotaOrdem(updateDTO.getNumeroNotaOrdem());
      }
      if (updateDTO.getOrdemObservacao() != null) {
          ordemCompra.setOrdemObservacao(updateDTO.getOrdemObservacao());
      }

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
      Long quantidade = itemDTO.getQuantidade();
      Double precoCompra = itemDTO.getPrecoCompra();
      Long numeroNota = itemDTO.getNumeroNota();
      String observacao = itemDTO.getObservacao();
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
                observacao);
        updatedItems.add(mapStruct.toItem(newItem));
      }
    }

    ordemCompraRepository.save(ordemCompra);

    return new ResponseEntity<>(updatedItems, HttpStatus.OK);
  }




}

