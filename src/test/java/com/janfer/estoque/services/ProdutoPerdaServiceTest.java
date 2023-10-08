package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.TipoProduto;
import com.janfer.estoque.repositories.ProdutoPerdaRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.services.ProdutoPerdaService;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class ProdutoPerdaServiceTest {

    @InjectMocks
    private ProdutoPerdaService produtoPerdaService;

    @Mock
    private ProdutoPerdaRepository produtoPerdaRepository;

    @Mock
    private ProdutoCapaRepository produtoCapaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWithActiveProduct() {
        // Criar uma perda de produto fictícia com um produto ativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);
        ProdutoPerda produtoPerda = new ProdutoPerda(1L, new Date(), 5L, "Motivo 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(true);

        // Configurar o comportamento do mock do produtoPerdaRepository
        when(produtoPerdaRepository.save(produtoPerda)).thenReturn(produtoPerda);

        // Chamar o método save da classe de serviço
        ProdutoPerda resultado = produtoPerdaService.save(produtoPerda);

        // Verificar se o resultado é igual à perda de produto fictícia
        assertEquals(produtoPerda, resultado);
    }

    @Test(expected = ProductDisableException.class)
    public void testSaveWithInactiveProduct() {
        // Criar uma perda de produto fictícia com um produto inativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, false);
        ProdutoPerda produtoPerda = new ProdutoPerda(1L, new Date(), 5L, "Motivo 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(false);

        // Chamar o método save da classe de serviço (deve lançar uma exceção)
        produtoPerdaService.save(produtoPerda);
    }

    @Test
    public void testDelete() {
        // Criar uma perda de produto fictícia para o teste
        ProdutoPerda produtoPerda = new ProdutoPerda(1L, new Date(), 5L, "Motivo 1", new ProdutoCapa());

        // Chamar o método delete da classe de serviço
        produtoPerdaService.delete(produtoPerda);

        // Verificar se o método delete do produtoPerdaRepository foi chamado com a perda de produto fictícia
        verify(produtoPerdaRepository, times(1)).delete(produtoPerda);
    }

    @Test
    public void testFindById() {
        // Criar uma perda de produto fictícia para o teste
        ProdutoPerda produtoPerda = new ProdutoPerda(1L, new Date(), 5L, "Motivo 1", new ProdutoCapa());

        // Configurar o comportamento do mock do produtoPerdaRepository
        when(produtoPerdaRepository.findById(1L)).thenReturn(Optional.of(produtoPerda));

        // Chamar o método findById da classe de serviço
        Optional<ProdutoPerda> resultado = produtoPerdaService.findById(1L);

        // Verificar se o resultado é igual à perda de produto fictícia
        assertEquals(Optional.of(produtoPerda), resultado);
    }

    @Test
    public void testCalcularSomaPerdas() {
        // Configurar o comportamento do mock do produtoPerdaRepository
        when(produtoPerdaRepository.calcularSomaPerdas(1L)).thenReturn(20.0);

        // Chamar o método calcularSomaPerdas da classe de serviço
        Double resultado = produtoPerdaService.calcularSomaPerdas(1L);

        // Verificar se o resultado é igual ao valor esperado
        assertEquals(20.0, resultado, 0.001); // Use uma tolerância para comparação de números de ponto flutuante, se necessário
    }


    @Test
    public void testFindAll() {
        // Criar uma lista de perdas de produtos fictícias para o teste
        List<ProdutoPerda> perdasFicticias = new ArrayList<>();
        perdasFicticias.add(new ProdutoPerda(1L, new Date(), 5L, "Motivo 1", new ProdutoCapa()));
        perdasFicticias.add(new ProdutoPerda(2L, new Date(), 10L, "Motivo 2", new ProdutoCapa()));
        perdasFicticias.add(new ProdutoPerda(3L, new Date(), 3L, "Motivo 3", new ProdutoCapa()));

        // Configurar o comportamento do mock do produtoPerdaRepository
        when(produtoPerdaRepository.findAll()).thenReturn(perdasFicticias);

        // Chamar o método findAll da classe de serviço
        List<ProdutoPerda> resultado = produtoPerdaService.findAll();

        // Verificar se a lista retornada contém os elementos esperados
        assertEquals(perdasFicticias, resultado);
    }
}
