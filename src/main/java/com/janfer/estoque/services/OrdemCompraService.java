package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.OrdemProdutoDTO;
import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.ItemOrdemCompraRepository;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdemCompraService {

  @Autowired
  private OrdemCompraRepository orderRepository;

  @Autowired
  private ItemOrdemCompraRepository orderItemRepository;

  @Autowired
  private MapStructMapper mapStructMapper;


  public OrdemCompra getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ObjectNotFoundException("Order not found with id: " + orderId));
  }

  public OrdemCompra createOrder() {
    OrdemCompra ordemCompra = new OrdemCompra();
    ordemCompra.setStatusOrdem(StatusOrdem.toEnum(2));
    return orderRepository.save(ordemCompra);
  }

  public ItemOrdemCompra addProductToOrder(OrdemCompra ordemCompra, ProdutoCapa produtoCapa, int quantidade) {
   ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
    itemOrdemCompra.setOrdemCompra(ordemCompra);
    itemOrdemCompra.setProdutoCapa(produtoCapa);
    itemOrdemCompra.setQuantidade(quantidade);

    return orderItemRepository.save(itemOrdemCompra);
  }


  public List<ItemOrdemCompra> getOrderItems(OrdemCompra ordemCompra) {
    return ordemCompra.getItemOrdemCompras();
  }

  public void faturarOrdem(OrdemCompra ordemCompra, String accessToken) {

    WebClient webClient = WebClient.create("http://localhost:8080");

    String produtoEntradaUrl = "/api/produtoEntrada/cadastrarAll";

    // Fazer uma chamada POST usando o WebClient
    webClient.post()
        .uri(produtoEntradaUrl)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .bodyValue(ordemCompra.getItemOrdemCompras())
        .retrieve()
        .onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(), clientResponse ->
            Mono.error(new RuntimeException("Erro de autorização: Token inválido ou ausente"))
        )
        .bodyToMono(Void.class)
        .subscribe(
            response -> System.out.println("Ordem faturada com sucesso."),
            error -> System.err.println("Erro ao faturar ordem: " + error.getMessage())
        );
    ordemCompra.setStatusOrdem(StatusOrdem.FATURADA);
  }

  public void estornarOrdem(OrdemCompra ordemCompra) {
    ordemCompra.setStatusOrdem(StatusOrdem.toEnum(2));
  }


}

