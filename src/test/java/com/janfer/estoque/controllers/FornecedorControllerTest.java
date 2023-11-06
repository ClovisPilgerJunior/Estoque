package com.janfer.estoque.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janfer.estoque.controllers.messages.FornecedorMessage;
import com.janfer.estoque.domain.dtos.FornecedorDTO;
import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.FornecedorService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FornecedorControllerTest {

    @InjectMocks
    private FornecedorController fornecedorController;

    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private MapStructMapper mapStructMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        // Crie um fornecedorDTO fictício
        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        fornecedorDTO.setEmpresa("Empresa Teste");

        // Configure o comportamento do mock do fornecedorService
        when(fornecedorService.existByEmpresa(fornecedorDTO.getEmpresa())).thenReturn(false);
        when(mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO)).thenReturn(new Fornecedor());

        // Chame o método create do fornecedorController
        ResponseEntity<FornecedorDTO> responseEntity = fornecedorController.create(fornecedorDTO);

        // Verifique se a resposta tem status HTTP 201 (CREATED)
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // Verifique se o corpo da resposta contém a mensagem esperada
        assertEquals("Fornecedor cadastrado com sucesso!", responseEntity.getBody());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateDuplicate() throws Exception {
        // Crie um fornecedorDTO fictício
        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        fornecedorDTO.setEmpresa("Empresa Teste");

        // Configure o comportamento do mock do fornecedorService para simular uma violação de integridade
        when(fornecedorService.existByEmpresa(fornecedorDTO.getEmpresa())).thenReturn(true);

        // Chame o método create do fornecedorController e espere uma exceção DataIntegrityViolationException
        fornecedorController.create(fornecedorDTO);
    }

    @Test
    public void testGetAll() throws Exception {
        // Crie uma lista fictícia de fornecedores
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor());
        fornecedores.add(new Fornecedor());

        // Configure o comportamento do mock do fornecedorService
        when(fornecedorService.findAll()).thenReturn(fornecedores);
        when(mapStructMapper.fornecedorAllToFornecedorDTO(fornecedores)).thenReturn(new ArrayList<>());

        // Chame o método getAll do fornecedorController
        ResponseEntity<List<FornecedorDTO>> responseEntity = fornecedorController.getAll();

        // Verifique se a resposta tem status HTTP 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verifique se o corpo da resposta é uma lista vazia (dependendo da implementação real do mapeamento)
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }

    @Test
    public void testFindById() throws Exception {
        // Crie um ID fictício
        Long id = 1L;

        // Crie um fornecedor fictício
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);

        // Configure o comportamento do mock do fornecedorService
        when(fornecedorService.findById(id)).thenReturn(Optional.of(fornecedor));

        // Chame o método findById do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.findById(id);

        // Verifique se a resposta tem status HTTP 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verifique se o corpo da resposta é o fornecedor fictício
        assertEquals(fornecedor, responseEntity.getBody());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        // Crie um ID fictício que não existe
        Long id = 1L;

        // Configure o comportamento do mock do fornecedorService para retornar um Optional vazio
        when(fornecedorService.findById(id)).thenReturn(Optional.empty());

        // Chame o método findById do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.findById(id);

        // Verifique se a resposta tem status HTTP 404 (NOT_FOUND)
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        // Verifique se o corpo da resposta é a mensagem de not found
        assertEquals("Fornecedor não encontrado", responseEntity.getBody());
    }

    @Test
    public void testUpdate() throws Exception {
        // Crie um ID fictício
        Long id = 1L;

        // Crie um fornecedorDTO fictício
        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        fornecedorDTO.setEmpresa("Nova Empresa");

        // Crie um fornecedor fictício existente
        Fornecedor fornecedorExistente = new Fornecedor();
        fornecedorExistente.setId(id);

        // Configure o comportamento do mock do fornecedorService
        when(fornecedorService.findById(id)).thenReturn(Optional.of(fornecedorExistente));
        when(mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO)).thenReturn(new Fornecedor());
        when(fornecedorService.existByEmpresaAndIdNot(fornecedorDTO.getEmpresa(), id)).thenReturn(false);

        // Chame o método update do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.update(id, fornecedorDTO);

        // Verifique se a resposta tem status HTTP 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verifique se o corpo da resposta é o fornecedor atualizado
        verify(fornecedorService, times(1)).save(any(Fornecedor.class)); // Verifique se o método save foi chamado
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateDuplicate() throws Exception {
        // Crie um ID fictício
        Long id = 1L;

        // Crie um fornecedorDTO fictício
        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        fornecedorDTO.setEmpresa("Nova Empresa");

        // Crie um fornecedor fictício existente
        Fornecedor fornecedorExistente = new Fornecedor();
        fornecedorExistente.setId(id);

        // Configure o comportamento do mock do fornecedorService para simular uma violação de integridade
        when(fornecedorService.findById(id)).thenReturn(Optional.of(fornecedorExistente));
        when(mapStructMapper.fornecedorToFornecedorDTO(fornecedorDTO)).thenReturn(new Fornecedor());
        when(fornecedorService.existByEmpresaAndIdNot(fornecedorDTO.getEmpresa(), id)).thenReturn(true);

        // Chame o método update do fornecedorController e espere uma exceção DataIntegrityViolationException
        fornecedorController.update(id, fornecedorDTO);
    }

    @Test
    public void testDelete() throws Exception {
        // Crie um ID fictício
        Long id = 1L;

        // Crie um fornecedor fictício existente
        Fornecedor fornecedorExistente = new Fornecedor();
        fornecedorExistente.setId(id);

        // Configure o comportamento do mock do fornecedorService
        when(fornecedorService.findById(id)).thenReturn(Optional.of(fornecedorExistente));

        // Chame o método delete do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.delete(id);

        // Verifique se a resposta tem status HTTP 200 (OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verifique se o método delete foi chamado no serviço
        verify(fornecedorService, times(1)).delete(fornecedorExistente);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        // Crie um ID fictício que não existe
        Long id = 1L;

        // Configure o comportamento do mock do fornecedorService para retornar um Optional vazio
        when(fornecedorService.findById(id)).thenReturn(Optional.empty());

        // Chame o método delete do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.delete(id);

        // Verifique se a resposta tem status HTTP 404 (NOT_FOUND)
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        // Verifique se a mensagem no corpo da resposta é a mensagem de "Fornecedor não encontrado."
        assertEquals("Fornecedor não encontrado", responseEntity.getBody());
    }

    @Test
    public void testUpdateFornecedorNotFound() {
        // Crie um ID fictício que não existe
        Long id = 1L;

        // Configure o comportamento do mock do fornecedorService para retornar um Optional vazio
        when(fornecedorService.findById(id)).thenReturn(Optional.empty());

        // Chame o método update do fornecedorController
        ResponseEntity<Object> responseEntity = fornecedorController.update(id, new FornecedorDTO());

        // Verifique se o serviço foi chamado com o ID correto
        verify(fornecedorService).findById(id);

        // Verifique se a resposta é um status HTTP 404
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Verifique se o corpo da resposta contém a mensagem de erro esperada
        assertEquals(FornecedorMessage.NOT_FOUND, responseEntity.getBody());
    }


}
