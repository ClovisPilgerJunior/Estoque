package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.enums.MedidaUnidade;
import com.janfer.estoque.domain.enums.Resuprimento;
import com.janfer.estoque.domain.enums.TipoEmpresa;
import com.janfer.estoque.domain.enums.TipoProduto;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.repositories.ProdutoEntradaRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ProdutoCapaServiceTest {

    @InjectMocks
    private ProdutoCapaService produtoCapaService;

    @Mock
    private ProdutoCapaRepository produtoCapaRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ProdutoEntradaRepository produtoEntradaRepository;

    @Mock
    private ProdutoEntradaService produtoEntradaService;

    @Mock
    private ProdutoPerdaService produtoPerdaService;

    @Mock
    private ProdutoSaidaService produtoSaidaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        // Criar um produtoCapa fictício para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true), 10L, 20L, Resuprimento.SALDO_ZERADO, true);

        // Configurar o comportamento do mock do fornecedorRepository
        when(fornecedorRepository.existsById(produtoCapa.getFornecedor().getId())).thenReturn(true);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.save(produtoCapa)).thenReturn(produtoCapa);
        when(produtoCapaRepository.isProdutoAtivoById(produtoCapa.getId())).thenReturn(true);

        // Chamar o método save da classe de serviço
        ProdutoCapa resultado = produtoCapaService.save(produtoCapa);

        // Verificar se o resultado é igual ao produtoCapa fictício
        assertEquals(produtoCapa, resultado);

        // Verificar se os métodos do repositório foram chamados
        verify(fornecedorRepository).existsById(produtoCapa.getFornecedor().getId());
        verify(produtoCapaRepository).save(produtoCapa);
        verify(produtoCapaRepository).isProdutoAtivoById(produtoCapa.getId());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testSaveWithNonExistentFornecedor() {
        // Criar um produtoCapa fictício com um fornecedor não existente para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true), 10L, 20L, Resuprimento.SALDO_ZERADO, true);

        // Configurar o comportamento do mock do fornecedorRepository
        when(fornecedorRepository.existsById(produtoCapa.getFornecedor().getId())).thenReturn(false);

        // Chamar o método save da classe de serviço (deve lançar uma exceção)
        produtoCapaService.save(produtoCapa);
    }

    @Test
    public void testDelete() {
        // Criar um produtoCapa fictício para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);

        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.existsById(produtoCapa.getId())).thenReturn(false);

        // Chamar o método delete da classe de serviço
        produtoCapaService.delete(produtoCapa);

        // Verificar se o método delete do repositório foi chamado com o produtoCapa correto
        verify(produtoCapaRepository).delete(produtoCapa);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testDeleteWithExistingProdutoEntrada() {
        // Criar um produtoCapa fictício para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);

        // Configurar o comportamento do mock do produtoEntradaRepository
        when(produtoEntradaRepository.existsById(produtoCapa.getId())).thenReturn(true);

        // Chamar o método delete da classe de serviço (deve lançar uma exceção)
        produtoCapaService.delete(produtoCapa);
    }


    @Test
    public void testFindById() {
        // Criar um produtoCapa fictício para o teste
        ProdutoCapa produtoCapa = new ProdutoCapa(1L, "Produto 1", TipoProduto.toEnum(1), MedidaUnidade.UNIDADE, null, 10L, 20L, Resuprimento.SALDO_ZERADO, true);

        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.findById(1L)).thenReturn(Optional.of(produtoCapa));

        // Chamar o método findById da classe de serviço
        Optional<ProdutoCapa> resultado = produtoCapaService.findById(1L);

        // Verificar se o resultado é igual ao produtoCapa fictício
        assertEquals(Optional.of(produtoCapa), resultado);
    }

    @Test
    public void testExistById() {
        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.existsById(1L)).thenReturn(true);

        // Chamar o método existById da classe de serviço
        boolean resultado = produtoCapaService.existById(1L);

        // Verificar se o resultado é true
        assertTrue(resultado);
    }

    @Test
    public void testIsProdutoAtivoById() {
        // Configurar o comportamento do mock do produtoCapaRepository
        when(produtoCapaRepository.isProdutoAtivoById(1L)).thenReturn(true);

        // Chamar o método isProdutoAtivoById da classe de serviço
        boolean resultado = produtoCapaService.isProdutoAtivoById(1L);

        // Verificar se o resultado é true
        assertTrue(resultado);
    }


    @Test
    public void testCalcularResuprimento() {
        assertEquals(Resuprimento.ESTOQUE_NEGATIVO, produtoCapaService.calcularResuprimento(-10.0, 10L, 20L));
        assertEquals(Resuprimento.SALDO_ZERADO, produtoCapaService.calcularResuprimento(0.0, 10L, 20L));
        assertEquals(Resuprimento.PRODUTO_EXCESSO, produtoCapaService.calcularResuprimento(30.0, 10L, 20L));
        assertEquals(Resuprimento.QUANTIDADE_IDEAL, produtoCapaService.calcularResuprimento(15.0, 10L, 20L));
        assertEquals(Resuprimento.COMPRAR_AGORA, produtoCapaService.calcularResuprimento(5.0, 10L, 20L));
    }

}
