package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import com.janfer.estoque.domain.entities.OrdemCompra;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.enums.StatusOrdem;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ItemOrdemCompraRepository;
import com.janfer.estoque.repositories.OrdemCompraRepository;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemCompraService {

  @Autowired
  private OrdemCompraRepository orderRepository;

  @Autowired
  private ItemOrdemCompraRepository orderItemRepository;

  @Autowired
  private ProdutoEntradaRepository produtoEntradaRepository;

  @Autowired
  private FornecedorRepository fornecedorRepository;

  @Autowired
  private MapStructMapper mapStructMapper;


  public OrdemCompra getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ObjectNotFoundException("Order not found with id: " + orderId));
  }

  public List<OrdemCompra> getAllOrdem() {
      return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

    public OrdemCompra generateOrder(OrdemCompra ordemCompra) {
        if (ordemCompra.getFornecedor() != null && ordemCompra.getFornecedor().getId() != null) {
            Long fornecedorId = ordemCompra.getFornecedor().getId();
            if (!fornecedorRepository.existsById(fornecedorId)) {
                throw new ObjectNotFoundException("Fornecedor não encontrado");
            }
        }
        ordemCompra.setDataEmissao(new Date());
        ordemCompra.setStatusOrdem(StatusOrdem.AGUARDANDO_LIBERACAO);
        // Salva a ordem de compra e retorna o objeto atualizado
        return orderRepository.save(ordemCompra);
    }

  public ItemOrdemCompra addProductToOrder(OrdemCompra ordemCompra,
                                           ProdutoCapa produtoCapa,
                                           Double quantidade,
                                           Double precoCompra,
                                           Double valorTotalItemOrdem,
                                           Long numeroNota,
                                           String observacao,
                                           String produtoCapaDesc) {
   ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
    itemOrdemCompra.setOrdemCompra(ordemCompra);
    itemOrdemCompra.setProdutoCapa(produtoCapa);
    itemOrdemCompra.setProdutoCapaDesc(produtoCapaDesc);
    itemOrdemCompra.setQuantidade(quantidade);
    itemOrdemCompra.setPrecoCompra(precoCompra);
    itemOrdemCompra.setValorTotalOrdem(valorTotalItemOrdem);
    itemOrdemCompra.setDataPedido(new Date());
    itemOrdemCompra.setObservacao(observacao);
    itemOrdemCompra.setNumeroNota(numeroNota);

    return orderItemRepository.save(itemOrdemCompra);
  }


    public void faturarOrdem(OrdemCompra ordemCompra) {
        for (ItemOrdemCompra item : ordemCompra.getItemOrdemCompras()) {
            ProdutoEntrada produtoEntrada = new ProdutoEntrada();
            produtoEntrada.setProdutoCapa(item.getProdutoCapa());
            produtoEntrada.setOrdemCompra(item.getOrdemCompra());
            produtoEntrada.setQuantidade(item.getQuantidade());
            produtoEntrada.setPrecoCompra(item.getPrecoCompra());
            produtoEntrada.setDataEntrega(new Date());
            produtoEntrada.setDataPedido(ordemCompra.getDataPedidoOrdemCompra());
            produtoEntrada.setObservacao(item.getObservacao());
            produtoEntrada.setNumeroNota(item.getOrdemCompra().getNumeroNotaOrdem());
            item.setDataRecebimento(new Date());
            // Configure outros campos conforme necessário
            orderItemRepository.save(item);
            produtoEntradaRepository.save(produtoEntrada);
        }

        // Atualize o status da ordem de compra
        ordemCompra.setStatusOrdem(StatusOrdem.RECEBIDO);
        ordemCompra.setDataRecebimentoOrdemCompra(new Date());
        ordemCompra.setValorTotal(ordemCompra.calcularValorTotal());
        orderRepository.save(ordemCompra);
    }


    public void liberarOrdem(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.LIBERADO);
      orderRepository.save(ordemCompra);
    }

    public void devolverOrdem(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.REVISAR);
      orderRepository.save(ordemCompra);
    }

    public void relizarPedidoOrdem(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.AGUARDANDO_RECEBIMENTO);
      ordemCompra.setDataPedidoOrdemCompra(new Date());
      orderRepository.save(ordemCompra);
    }

    public void revisarOrdem(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.AGUARDANDO_LIBERACAO);
      orderRepository.save(ordemCompra);
    }

    public void cancelarOrdem(OrdemCompra ordemCompra) {
      ordemCompra.setStatusOrdem(StatusOrdem.CANCELADO);
      orderRepository.save(ordemCompra);
    }

    public void estornarOrdem(OrdemCompra ordemCompra ,Long orderId) {
        ordemCompra.setStatusOrdem(StatusOrdem.AGUARDANDO_RECEBIMENTO);
        ordemCompra.setDataRecebimentoOrdemCompra(null);
        orderRepository.save(ordemCompra);
        // Obter a lista de ProdutoEntrada associadas à ordem de compra
        List<ProdutoEntrada> produtoEntradas = produtoEntradaRepository.findByOrdemCompraId(orderId);
        // Deletar todas as ProdutoEntrada na lista de uma vez
        produtoEntradaRepository.deleteAll(produtoEntradas);
    }

    public ItemOrdemCompra updateItem(ItemOrdemCompra item) {
        return orderItemRepository.save(item);
    }

    public void deleteItemFromOrder(OrdemCompra ordemCompra, Long produtoCapaId) {
        // Busca o item na lista de itens da OrdemCompra pelo produtoCapaId
        Optional<ItemOrdemCompra> itemToRemove = ordemCompra.getItemOrdemCompras().stream()
                .filter(item -> item.getProdutoCapa().getId().equals(produtoCapaId))
                .findFirst();

        // Se o item foi encontrado, remove-o da lista de itens da OrdemCompra
        if (itemToRemove.isPresent()) {
            ItemOrdemCompra item = itemToRemove.get();
            ordemCompra.getItemOrdemCompras().remove(item);

            // Se você estiver usando JPA/Hibernate, pode ser necessário remover o item do contexto de persistência
            // para que ele seja excluído do banco de dados na próxima operação de salvamento.
            // Por exemplo:
            orderItemRepository.delete(item);

            // Salva a OrdemCompra atualizada
            orderRepository.save(ordemCompra);
        } else {
            // Opcional: Trate o caso em que o item não foi encontrado
            // Por exemplo, lançar uma exceção ou registrar um log
        }
    }


    public OrdemCompra updateDataPrevisao(Long id, Date dataPrevisaoEntrega) {
        OrdemCompra ordemCompra = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de compra não encontrada"));
        ordemCompra.setDataPrevisaoEntrega(dataPrevisaoEntrega);
        return orderRepository.save(ordemCompra);
    }
}

