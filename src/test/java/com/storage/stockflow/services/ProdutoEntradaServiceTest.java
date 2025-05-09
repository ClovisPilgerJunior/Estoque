package com.storage.stockflow.services;

import com.storage.stockflow.domain.entities.ProdutoCapa;
import com.storage.stockflow.domain.entities.ProdutoEntrada;
import com.storage.stockflow.domain.enums.MedidaUnidade;
import com.storage.stockflow.domain.enums.Resuprimento;
import com.storage.stockflow.domain.enums.TipoProduto;
import com.storage.stockflow.repositories.ProdutoCapaRepository;
import com.storage.stockflow.repositories.ProdutoEntradaRepository;
import com.storage.stockflow.services.exceptions.ProductDisableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProdutoEntradaServiceTest {

    @InjectMocks
    private ProdutoEntradaService produtoEntradaService;

    @Mock
    private ProdutoEntradaRepository produtoEntradaRepository;

    @Mock
    private ProdutoCapaRepository produtoCapaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test(expected = ProductDisableException.class)
    public void testSaveWithInactiveProduct() {
        // Criar uma entrada de produto fictícia com um produto inativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, false);
        ProdutoEntrada produtoEntrada = new ProdutoEntrada(1L, 123456L, null, null, 10.0, 100L, "Observação 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(false);

        // Chamar o método save da classe de serviço
        produtoEntradaService.save(produtoEntrada);

        // Verificar se uma exceção ProductDisableException é lançada
    }

    @Test
    public void testRecuperarUltimoPrecoCompraWithValidProduct() {
        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.recuperarUltimoPrecoCompra(1L)).thenReturn(10.0);

        // Chamar o método recuperarUltimoPrecoCompra da classe de serviço
        Double resultado = produtoEntradaService.recuperarUltimoPrecoCompra(1L);

        // Verificar se o resultado é igual ao valor esperado
        assertEquals(10.0, resultado, 0.0);
    }

    @Test
    public void testRecuperarUltimoPrecoCompraWithInvalidProduct() {
        // Configurar o comportamento do mock do produtoEntradaRepository para um produto inexistente
        when(produtoEntradaRepository.recuperarUltimoPrecoCompra(2L)).thenReturn(null);

        // Chamar o método recuperarUltimoPrecoCompra da classe de serviço para um produto inexistente
        Double resultado = produtoEntradaService.recuperarUltimoPrecoCompra(2L);

        // Verificar se o resultado é nulo (produto inexistente)
        assertNull(resultado);
    }

    @Test
    public void testFindAll() {
        // Criar uma lista fictícia de entradas de produtos para o teste
        List<ProdutoEntrada> produtoEntradas = new ArrayList<>();
        ProdutoEntrada produtoEntrada1 = new ProdutoEntrada(1L, 123456L, null, null, 10.0, 100L, "Observação 1", new ProdutoCapa());
        produtoEntradas.add(produtoEntrada1);

        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.findAll()).thenReturn(produtoEntradas);

        // Chamar o método findAll da classe de serviço
        List<ProdutoEntrada> resultado = produtoEntradaService.findAll();

        // Verificar se o resultado possui a quantidade esperada de entradas de produtos
        assertEquals(1, resultado.size());
    }


    @Test
    public void testDelete() {
        // Criar uma entrada de produto fictícia para o teste
        ProdutoEntrada produtoEntrada = new ProdutoEntrada(1L, 123456L, null, null, 10.0, 100L, "Observação 1", new ProdutoCapa());

        // Chamar o método delete da classe de serviço
        produtoEntradaService.delete(produtoEntrada);

        // Verificar se o método delete do produtoEntradaRepository foi chamado com a entrada de produto fictícia
        verify(produtoEntradaRepository, times(1)).delete(produtoEntrada);
    }

    @Test
    public void testFindById() {
        // Criar uma entrada de produto fictícia para o teste
        ProdutoEntrada produtoEntrada = new ProdutoEntrada(1L, 123456L, null, null, 10.0, 100L, "Observação 1", new ProdutoCapa());

        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.findById(1L)).thenReturn(Optional.of(produtoEntrada));

        // Chamar o método findById da classe de serviço
        Optional<ProdutoEntrada> resultado = produtoEntradaService.findById(1L);

        // Verificar se o resultado é igual à entrada de produto fictícia
        assertEquals(Optional.of(produtoEntrada), resultado);
    }

    @Test
    public void testCalcularSomaEntradas() {
        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.calcularSomaEntradas(1L)).thenReturn(100.0);

        // Chamar o método calcularSomaEntradas da classe de serviço
        Double resultado = produtoEntradaService.calcularSomaEntradas(1L);

        // Verificar se o resultado é igual ao valor esperado
        assertEquals(100.0, resultado, 0.0);
    }

    @Test
    public void testRecuperarUltimoPrecoCompra() {
        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.recuperarUltimoPrecoCompra(1L)).thenReturn(10.0);

        // Chamar o método recuperarUltimoPrecoCompra da classe de serviço
        Double resultado = produtoEntradaService.recuperarUltimoPrecoCompra(1L);

        // Verificar se o resultado é igual ao valor esperado
        assertEquals(10.0, resultado, 0.0);
    }

    @Test
    public void testSaveWithActiveProduct() {
        // Criar uma entrada de produto fictícia com um produto ativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);
        ProdutoEntrada produtoEntrada = new ProdutoEntrada(1L, 123456L, null, null, 10.0, 100L, "Observação 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(true);

        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.save(produtoEntrada)).thenReturn(produtoEntrada);

        // Chamar o método save da classe de serviço
        ProdutoEntrada resultado = produtoEntradaService.save(produtoEntrada);

        // Verificar se o resultado é igual à entrada de produto fictícia
        assertEquals(produtoEntrada, resultado);
    }

}
