package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.ProdutoCapaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoCapaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProdutoCapaControllerTest {

    @InjectMocks
    private ProdutoCapaController produtoCapaController;

    @Mock
    private ProdutoCapaService produtoCapaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProdutoCapaAlreadyExists() {
        ProdutoCapaPostDTO produtoCapaDTO = new ProdutoCapaPostDTO();
        when(produtoCapaService.existByDesc(produtoCapaDTO.getDesc())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> {
            produtoCapaController.create(produtoCapaDTO);
        });
    }

    @Test
    void testUpdateProdutoCapaNotFound() {
        Long id = 1L;
        ProdutoCapaPostDTO produtoCapaDTO = new ProdutoCapaPostDTO();
        Optional<ProdutoCapa> produtoCapaOptional = Optional.empty();
        when(produtoCapaService.findById(id)).thenReturn(produtoCapaOptional);

        ResponseEntity<Object> responseEntity = produtoCapaController.update(id, produtoCapaDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Produto não encontrado", responseEntity.getBody());
    }

    @Test
    void testUpdateProdutoCapaAlreadyExists() {
        Long id = 1L;
        ProdutoCapaPostDTO produtoCapaDTO = new ProdutoCapaPostDTO();
        Optional<ProdutoCapa> produtoCapaOptional = Optional.of(new ProdutoCapa());
        when(produtoCapaService.findById(id)).thenReturn(produtoCapaOptional);
        when(produtoCapaService.existByDescAndIdNot(produtoCapaDTO.getDesc(), id)).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> {
            produtoCapaController.update(id, produtoCapaDTO);
        });
    }

    @Test
    void testDeleteProdutoCapaSuccess() {
        Long id = 1L;
        ProdutoCapa produtoCapa = new ProdutoCapa();
        Optional<ProdutoCapa> produtoCapaOptional = Optional.of(produtoCapa);
        when(produtoCapaService.findById(id)).thenReturn(produtoCapaOptional);

        ResponseEntity<Object> responseEntity = produtoCapaController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Produto 1 excluído com sucesso", responseEntity.getBody());
    }

    @Test
    void testDeleteProdutoCapaNotFound() {
        Long id = 1L;
        Optional<ProdutoCapa> produtoCapaOptional = Optional.empty();
        when(produtoCapaService.findById(id)).thenReturn(produtoCapaOptional);

        ResponseEntity<Object> responseEntity = produtoCapaController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Produto não encontrado", responseEntity.getBody());
    }

    @Test
    void testFindAllProdutos() {
        List<ProdutoCapa> produtoCapas = new ArrayList<>();
        produtoCapas.add(new ProdutoCapa());
        when(produtoCapaService.findAll()).thenReturn(produtoCapas);

        ResponseEntity<List<ProdutoCapaGetDTO>> responseEntity = produtoCapaController.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testFindProdutoByIdNotFound() {
        Long id = 1L;
        Optional<ProdutoCapa> produtoCapaOptional = Optional.empty();
        when(produtoCapaService.findById(id)).thenReturn(produtoCapaOptional);

        assertThrows(ObjectNotFoundException.class, () -> {
            produtoCapaController.findById(id);
        });
    }
}
