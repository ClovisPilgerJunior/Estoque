package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.ProdutoEntradaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoEntradaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoEntrada;
import com.janfer.estoque.domain.mappers.MapStructMapper;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdutoEntradaControllerTest {

    @InjectMocks
    private ProdutoEntradaController produtoEntradaController;

    @Mock
    private ProdutoEntradaService produtoEntradaService;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private ProdutoCapaService produtoCapaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindByIdProdutoEntrada() {
        Long id = 1L;
        ProdutoEntrada produtoEntrada = new ProdutoEntrada();
        ProdutoEntradaGetDTO produtoEntradaGetDTO = new ProdutoEntradaGetDTO();

        when(produtoEntradaService.findById(id)).thenReturn(Optional.of(produtoEntrada));
        when(mapStructMapper.produtoEntradaToProdutoEntradaGetDTO(produtoEntrada)).thenReturn(produtoEntradaGetDTO);

        ResponseEntity<ProdutoEntradaGetDTO> response = produtoEntradaController.findById(id);

        verify(produtoEntradaService, times(1)).findById(id);
        verify(mapStructMapper, times(1)).produtoEntradaToProdutoEntradaGetDTO(produtoEntrada);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoEntradaGetDTO, response.getBody());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testFindByIdProdutoEntradaNotFound() {
        Long id = 1L;

        when(produtoEntradaService.findById(id)).thenReturn(Optional.empty());

        produtoEntradaController.findById(id);
    }

    @Test
    public void testDeleteProdutoEntrada() {
        Long id = 1L;
        ProdutoEntrada produtoEntrada = new ProdutoEntrada();

        when(produtoEntradaService.findById(id)).thenReturn(Optional.of(produtoEntrada));

        ResponseEntity<Object> response = produtoEntradaController.delete(id);

        verify(produtoEntradaService, times(1)).findById(id);
        verify(produtoEntradaService, times(1)).delete(produtoEntrada);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto 1 excluído com sucesso", response.getBody());
    }

    @Test
    public void testDeleteProdutoEntradaNotFound() {
        Long id = 1L;

        when(produtoEntradaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoEntradaController.delete(id);

        verify(produtoEntradaService, times(1)).findById(id);
        verifyZeroInteractions(produtoEntradaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto não encontrado", response.getBody());
    }

    @Test
    public void testCreateProdutoEntrada() {
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        produtoEntradaDTO.setProdutoCapa(new ProdutoCapa());
        produtoEntradaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(true);

        ResponseEntity<String> response = produtoEntradaController.criarProdutoEntrada(produtoEntradaDTO);

        verify(produtoEntradaService, times(1)).save(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ProdutoEntrada cadastrado com sucesso.", response.getBody());
    }

    @Test
    public void testCreateProdutoEntradaProdutoCapaNaoEncontrado() {
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        produtoEntradaDTO.setProdutoCapa(new ProdutoCapa());
        produtoEntradaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(false);

        ResponseEntity<String> response = produtoEntradaController.criarProdutoEntrada(produtoEntradaDTO);

        verifyZeroInteractions(produtoEntradaService);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ProdutoCapa correspondente não encontrado.", response.getBody());
    }

    @Test
    public void testUpdateProdutoEntrada() {
        Long id = 1L;
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();
        ProdutoEntrada produtoEntrada = new ProdutoEntrada();

        when(produtoEntradaService.findById(id)).thenReturn(Optional.of(produtoEntrada));

        ResponseEntity<Object> response = produtoEntradaController.update(id, produtoEntradaDTO);

        verify(produtoEntradaService, times(1)).findById(id);
        verify(produtoEntradaService, times(1)).save(any(ProdutoEntrada.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateProdutoEntradaNotFound() {
        Long id = 1L;
        ProdutoEntradaPostDTO produtoEntradaDTO = new ProdutoEntradaPostDTO();

        when(produtoEntradaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoEntradaController.update(id, produtoEntradaDTO);

        verify(produtoEntradaService, times(1)).findById(id);
        verifyZeroInteractions(produtoEntradaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fornecedor não encontrado", response.getBody());
    }

    private void verifyZeroInteractions(ProdutoEntradaService produtoEntradaService) {
    }
}
