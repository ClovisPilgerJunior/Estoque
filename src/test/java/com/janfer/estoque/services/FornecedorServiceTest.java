package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.enums.TipoEmpresa;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FornecedorServiceTest {

    @InjectMocks
    private FornecedorService fornecedorService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ProdutoCapaRepository produtoCapaRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindAll() {
        // Criar uma lista de fornecedores fictícia para simular a resposta do repositório
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true));
        fornecedores.add(new Fornecedor(2L, "Empresa 2", "Nome 2", TipoEmpresa.FORNECEDOR, "email2@example.com", "987654321", "Endereço 2", true));

        // Configurar o comportamento do mock do repositório
        when(fornecedorRepository.findAll()).thenReturn(fornecedores);

        // Chamar o método findAll da classe de serviço
        List<Fornecedor> resultado = fornecedorService.findAll();

        // Verificar se o resultado é igual à lista fictícia
        assertEquals(fornecedores, resultado);
    }

    @Test
    public void testSave() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do repositório
        when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);

        // Chamar o método save da classe de serviço
        Fornecedor resultado = fornecedorService.save(fornecedor);

        // Verificar se o resultado é igual ao fornecedor fictício
        assertEquals(fornecedor, resultado);

        // Verificar se o método save do repositório foi chamado com o fornecedor correto
        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    public void testDelete() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do produtoCapaRepository.existsById para retornar false
        when(produtoCapaRepository.existsById(fornecedor.getId())).thenReturn(false);

        // Chamar o método delete da classe de serviço
        fornecedorService.delete(fornecedor);

        // Verificar se o método delete do repositório foi chamado com o fornecedor correto
        verify(fornecedorRepository).delete(fornecedor);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testDeleteWithExistingProduct() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do produtoCapaRepository.existsById para retornar true
        when(produtoCapaRepository.existsById(fornecedor.getId())).thenReturn(true);

        // Chamar o método delete da classe de serviço
        fornecedorService.delete(fornecedor);
    }

    @Test
    public void testFindById() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do repositório para retornar o fornecedor fictício
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));

        // Chamar o método findById da classe de serviço
        Optional<Fornecedor> resultado = fornecedorService.findById(1L);

        // Verificar se o resultado é igual ao fornecedor fictício
        assertEquals(Optional.of(fornecedor), resultado);
    }

    @Test
    public void testExistByEmpresaAndIdNot() {
        // Configurar o comportamento do mock do repositório para retornar true
        when(fornecedorRepository.existsByEmpresaAndIdNot("Empresa1", 1L)).thenReturn(true);

        // Chamar o método existByEmpresaAndIdNot da classe de serviço
        boolean resultado = fornecedorService.existByEmpresaAndIdNot("Empresa1", 1L);

        // Verificar se o resultado é true
        assertTrue(resultado);
    }

    @Test
    public void testExistByEmpresa() {
        // Configurar o comportamento do mock do repositório para retornar true
        when(fornecedorRepository.existsByEmpresa("Empresa1")).thenReturn(true);

        // Chamar o método existByEmpresa da classe de serviço
        boolean resultado = fornecedorService.existByEmpresa("Empresa1");

        // Verificar se o resultado é true
        assertTrue(resultado);
    }

    @Test
    public void testDeleteWithNonExistingProduct() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do produtoCapaRepository.existsById para retornar false
        when(produtoCapaRepository.existsById(fornecedor.getId())).thenReturn(false);

        // Chamar o método delete da classe de serviço
        fornecedorService.delete(fornecedor);

        // Verificar se o método delete do repositório foi chamado com o fornecedor correto
        verify(fornecedorRepository).delete(fornecedor);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testDeleteWithExistingProductFornecedor() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do produtoCapaRepository.existsById para retornar true
        when(produtoCapaRepository.existsById(fornecedor.getId())).thenReturn(true);

        // Chamar o método delete da classe de serviço
        fornecedorService.delete(fornecedor);
    }

    @Test
    public void testDeleteWithoutProducts() {
        // Criar um fornecedor fictício para o teste
        Fornecedor fornecedor = new Fornecedor(1L, "Empresa 1", "Nome 1", TipoEmpresa.CLIENTE, "email1@example.com", "123456789", "Endereço 1", true);

        // Configurar o comportamento do mock do produtoCapaRepository.existsById para retornar false
        when(produtoCapaRepository.existsById(fornecedor.getId())).thenReturn(false);

        // Chamar o método delete da classe de serviço
        fornecedorService.delete(fornecedor);

        // Verificar se o método delete do repositório foi chamado com o fornecedor correto
        verify(fornecedorRepository).delete(fornecedor);
    }

    @Test
    public void testDeleteFornecedorWithExistingProduct() {
        // Simule um cenário onde o produto existe
        Long fornecedorId = 1L;
        Mockito.when(produtoCapaRepository.existsById(fornecedorId)).thenReturn(true);

        // Verifique se uma exceção é lançada
        try {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(fornecedorId);

            fornecedorService.delete(fornecedor);
        } catch (DataIntegrityViolationException e) {
            // Verifique se a exceção esperada é lançada
            // Você pode adicionar mais verificações aqui, se necessário
            assert (e.getMessage().equals("Não é possível excluir um fornecedor com produto existente"));
        }

        // Verifique se o método fornecedorRepository.delete(fornecedor) nunca é chamado
        Mockito.verify(fornecedorRepository, Mockito.never()).delete(Mockito.any());
    }

}