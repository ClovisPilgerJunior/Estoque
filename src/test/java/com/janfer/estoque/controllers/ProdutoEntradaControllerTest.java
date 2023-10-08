package com.janfer.estoque.controllers;

import com.janfer.estoque.controllers.ProdutoEntradaController;
import com.janfer.estoque.domain.dtos.ProdutoEntradaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoEntradaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.domain.mappers.MapStructMapperImpl;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoEntradaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoEntradaControllerTest {

    @Autowired
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private ProdutoEntradaController produtoEntradaController;

    @Mock
    private ProdutoEntradaService produtoEntradaService;

    @Mock
    private ProdutoCapaService produtoCapaService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllProdutoEntrada() {
        List<ProdutoEntrada> produtoEntradas = new ArrayList<>();
        produtoEntradas.add(new ProdutoEntrada());
        when(produtoEntradaService.findAll()).thenReturn(produtoEntradas);

        ResponseEntity<List<ProdutoEntradaGetDTO>> responseEntity = produtoEntradaController.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testFindProdutoEntradaByIdSuccess() {
        Long id = 1L;
        ProdutoEntrada produtoEntrada = new ProdutoEntrada();
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.of(produtoEntrada);
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        ResponseEntity<ProdutoEntradaGetDTO> responseEntity = produtoEntradaController.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testFindProdutoEntradaByIdNotFound() {
        Long id = 1L;
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.empty();
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        produtoEntradaController.findById(id);
    }

    @Test
    public void testDeleteProdutoEntradaSuccess() {
        Long id = 1L;
        ProdutoEntrada produtoEntrada = new ProdutoEntrada();
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.of(produtoEntrada);
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        ResponseEntity<Object> responseEntity = produtoEntradaController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Produto 1 excluído com sucesso", responseEntity.getBody());
    }

    @Test
    public void testDeleteProdutoEntradaNotFound() {
        Long id = 1L;
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.empty();
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        ResponseEntity<Object> responseEntity = produtoEntradaController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Produto não encontrado", responseEntity.getBody());
    }

    @Test
    public void testCriarProdutoEntradaSuccess() {
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        produtoEntradaDTO.setProdutoCapa(new ProdutoCapa());
        when(produtoCapaService.existById(anyLong())).thenReturn(true);

        ResponseEntity<String> responseEntity = produtoEntradaController.criarProdutoEntrada(produtoEntradaDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("ProdutoEntrada cadastrado com sucesso.", responseEntity.getBody());
    }

    @Test
    public void testCriarProdutoEntradaProdutoCapaNotFound() {
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        produtoEntradaDTO.setProdutoCapa(new ProdutoCapa());
        when(produtoCapaService.existById(anyLong())).thenReturn(false);

        ResponseEntity<String> responseEntity = produtoEntradaController.criarProdutoEntrada(produtoEntradaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("ProdutoCapa correspondente não encontrado.", responseEntity.getBody());
    }

    @Test
    public void testUpdateProdutoEntradaSuccess() {
        Long id = 1L;
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.of(new ProdutoEntrada());
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        ResponseEntity<Object> responseEntity = produtoEntradaController.update(id, produtoEntradaDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateProdutoEntradaNotFound() {
        Long id = 1L;
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        Optional<ProdutoEntrada> produtoEntradaOptional = Optional.empty();
        when(produtoEntradaService.findById(id)).thenReturn(produtoEntradaOptional);

        ResponseEntity<Object> responseEntity = produtoEntradaController.update(id, produtoEntradaDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Fornecedor não encontrado", responseEntity.getBody());
    }
}
