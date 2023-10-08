package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.Setor;
import com.janfer.estoque.domain.enums.TipoProduto;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoSaidaRepository;
import com.janfer.estoque.services.exceptions.ProductDisableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdutoSaidaServiceTest {

    @InjectMocks
    private ProdutoSaidaService produtoSaidaService;

    @Mock
    private ProdutoSaidaRepository produtoSaidaRepository;

    @Mock
    private ProdutoCapaRepository produtoCapaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWithActiveProduct() {
        // Criar uma saída de produto fictícia com um produto ativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);
        ProdutoSaida produtoSaida = new ProdutoSaida(1L, new Date(), 5L, "João", Setor.toEnum(1), "Observação 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(true);

        // Configurar o comportamento do mock do produtoSaidaRepository
        when(produtoSaidaRepository.save(produtoSaida)).thenReturn(produtoSaida);

        // Chamar o método save da classe de serviço
        ProdutoSaida resultado = produtoSaidaService.save(produtoSaida);

        // Verificar se o resultado é igual à saída de produto fictícia
        assertEquals(produtoSaida, resultado);
    }

    @Test(expected = ProductDisableException.class)
    public void testSaveWithInactiveProduct() {
        // Criar uma saída de produto fictícia com um produto inativo para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, false);
        ProdutoSaida produtoSaida = new ProdutoSaida(1L, new Date(), 5L, "João", Setor.toEnum(1), "Observação 1", produtoCapa);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(false);

        // Chamar o método save da classe de serviço (deve lançar uma exceção)
        produtoSaidaService.save(produtoSaida);
    }

    @Test
    public void testDelete() {
        // Criar uma saída de produto fictícia para o teste
        ProdutoSaida produtoSaida = new ProdutoSaida(1L, new Date(), 5L, "João", Setor.toEnum(1), "Observação 1", new ProdutoCapa());

        // Chamar o método delete da classe de serviço
        produtoSaidaService.delete(produtoSaida);

        // Verificar se o método delete do produtoSaidaRepository foi chamado com a saída de produto fictícia
        verify(produtoSaidaRepository, times(1)).delete(produtoSaida);
    }

    @Test
    public void testFindById() {
        // Criar uma saída de produto fictícia para o teste
        ProdutoSaida produtoSaida = new ProdutoSaida(1L, new Date(), 5L, "João", Setor.toEnum(1), "Observação 1", new ProdutoCapa());

        // Configurar o comportamento do mock do produtoSaidaRepository
        when(produtoSaidaRepository.findById(1L)).thenReturn(Optional.of(produtoSaida));

        // Chamar o método findById da classe de serviço
        Optional<ProdutoSaida> resultado = produtoSaidaService.findById(1L);

        // Verificar se o resultado é igual à saída de produto fictícia
        assertEquals(Optional.of(produtoSaida), resultado);
    }

    @Test
    public void testCalcularSomaSaida() {
        // Configurar o comportamento do mock do produtoSaidaRepository
        when(produtoSaidaRepository.calcularSomaSaidas(1L)).thenReturn(15.0);

        // Chamar o método calcularSomaSaida da classe de serviço
        Double resultado = produtoSaidaService.calcularSomaSaida(1L);

        // Verificar se o resultado é igual ao valor esperado
        assertEquals(15.0, resultado, 0.001); // Use uma tolerância para comparação de números de ponto flutuante, se necessário
    }

    @Test
    public void testFindAll() {
        // Criar uma lista de saídas de produtos fictícias para o teste
        List<ProdutoSaida> saidasFicticias = new ArrayList<>();
        saidasFicticias.add(new ProdutoSaida(1L, new Date(), 5L, "João", Setor.toEnum(1), "Observação 1", new ProdutoCapa()));
        saidasFicticias.add(new ProdutoSaida(2L, new Date(), 10L, "Maria", Setor.toEnum(2), "Observação 2", new ProdutoCapa()));
        saidasFicticias.add(new ProdutoSaida(3L, new Date(), 3L, "José", Setor.toEnum(3), "Observação 3", new ProdutoCapa()));

        // Configurar o comportamento do mock do produtoSaidaRepository
        when(produtoSaidaRepository.findAll()).thenReturn(saidasFicticias);

        // Chamar o método findAll da classe de serviço
        List<ProdutoSaida> resultado = produtoSaidaService.findAll();

        // Verificar se a lista retornada contém os elementos esperados
        assertEquals(saidasFicticias, resultado);
    }
}
