package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.ProdutoCapaPostDTO;
import com.janfer.estoque.domain.dtos.ProdutoPerdaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoPerdaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoPerda;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoPerdaService;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProdutoPerdaControllerTest {

    @InjectMocks
    private ProdutoPerdaController produtoPerdaController;

    @Mock
    private ProdutoPerdaService produtoPerdaService;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private ProdutoCapaService produtoCapaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProdutoPerda() {
        ProdutoPerdaPostDTO produtoPerdaDTO = new ProdutoPerdaPostDTO();
        produtoPerdaDTO.setProdutoCapa(new ProdutoCapa());
        produtoPerdaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(true);

        ResponseEntity<Object> response = produtoPerdaController.create(produtoPerdaDTO);

        verify(produtoPerdaService, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Produto cadastrado com sucesso", response.getBody());
    }

    @Test
    public void testCreateProdutoPerdaProdutoCapaNaoEncontrado() {
        ProdutoPerdaPostDTO produtoPerdaDTO = new ProdutoPerdaPostDTO();
        produtoPerdaDTO.setProdutoCapa(new ProdutoCapa());
        produtoPerdaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(false);

        ResponseEntity<Object> response = produtoPerdaController.create(produtoPerdaDTO);

        verifyZeroInteractions(produtoPerdaService);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ProdutoCapa correspondente não encontrado.", response.getBody());
    }

    private void verifyZeroInteractions(ProdutoPerdaService produtoPerdaService) {
    }

    @Test
    public void testFindAllProdutoPerda() {
        List<ProdutoPerda> produtoPerdas = new ArrayList<>();
        List<ProdutoPerdaGetDTO> produtoPerdaGetDTOs = new ArrayList<>();

        when(produtoPerdaService.findAll()).thenReturn(produtoPerdas);
        when(mapStructMapper.produtoPerdaGetDTOAllToProdutoPerda(produtoPerdas)).thenReturn(produtoPerdaGetDTOs);

        ResponseEntity<List<ProdutoPerdaGetDTO>> response = produtoPerdaController.findAll();

        verify(produtoPerdaService, times(1)).findAll();
        verify(mapStructMapper, times(1)).produtoPerdaGetDTOAllToProdutoPerda(produtoPerdas);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoPerdaGetDTOs, response.getBody());
    }

    @Test
    public void testFindByIdProdutoPerda() {
        Long id = 1L;
        ProdutoPerda produtoPerda = new ProdutoPerda();
        ProdutoPerdaGetDTO produtoPerdaGetDTO = new ProdutoPerdaGetDTO();

        when(produtoPerdaService.findById(id)).thenReturn(Optional.of(produtoPerda));
        when(mapStructMapper.produtoPerdaGetDTOToProdutoPerda(produtoPerda)).thenReturn(produtoPerdaGetDTO);

        ResponseEntity<ProdutoPerdaGetDTO> response = produtoPerdaController.findById(id);

        verify(produtoPerdaService, times(1)).findById(id);
        verify(mapStructMapper, times(1)).produtoPerdaGetDTOToProdutoPerda(produtoPerda);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoPerdaGetDTO, response.getBody());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testFindByIdProdutoPerdaNotFound() {
        Long id = 1L;

        when(produtoPerdaService.findById(id)).thenReturn(Optional.empty());

        produtoPerdaController.findById(id);
    }

    @Test
    public void testDeleteProdutoPerda() {
        Long id = 1L;
        ProdutoPerda produtoPerda = new ProdutoPerda();

        when(produtoPerdaService.findById(id)).thenReturn(Optional.of(produtoPerda));

        ResponseEntity<Object> response = produtoPerdaController.delete(id);

        verify(produtoPerdaService, times(1)).findById(id);
        verify(produtoPerdaService, times(1)).delete(produtoPerda);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto 1 excluído com sucesso", response.getBody());
    }

    @Test
    public void testDeleteProdutoPerdaNotFound() {
        Long id = 1L;

        when(produtoPerdaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoPerdaController.delete(id);

        verify(produtoPerdaService, times(1)).findById(id);
        verifyZeroInteractions(produtoPerdaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto não encontrado", response.getBody());
    }

    @Test
    public void testUpdateProdutoPerda() {
        Long id = 1L;
        ProdutoPerdaPostDTO produtoPerdaDTO = new ProdutoPerdaPostDTO();
        ProdutoPerda produtoPerda = new ProdutoPerda();

        when(produtoPerdaService.findById(id)).thenReturn(Optional.of(produtoPerda));

        ResponseEntity<Object> response = produtoPerdaController.update(id, produtoPerdaDTO);

        verify(produtoPerdaService, times(1)).findById(id);
        verify(produtoPerdaService, times(1)).save(any(ProdutoPerda.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateProdutoPerdaNotFound() {
        Long id = 1L;
        ProdutoPerdaPostDTO produtoPerdaDTO = new ProdutoPerdaPostDTO();

        when(produtoPerdaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoPerdaController.update(id, produtoPerdaDTO);

        verify(produtoPerdaService, times(1)).findById(id);
        verifyZeroInteractions(produtoPerdaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto Não encontrado", response.getBody());
    }
}
