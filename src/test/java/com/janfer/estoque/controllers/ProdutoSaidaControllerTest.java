package com.janfer.estoque.controllers;

import com.janfer.estoque.controllers.ProdutoSaidaController;
import com.janfer.estoque.domain.dtos.ProdutoSaidaGetDTO;
import com.janfer.estoque.domain.dtos.ProdutoSaidaPostDTO;
import com.janfer.estoque.domain.entities.ProdutoCapa;
import com.janfer.estoque.domain.entities.ProdutoSaida;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.services.ProdutoCapaService;
import com.janfer.estoque.services.ProdutoSaidaService;
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
@RunWith(SpringRunner.class)
public class ProdutoSaidaControllerTest {

    @InjectMocks
    private ProdutoSaidaController produtoSaidaController;

    @Mock
    private ProdutoSaidaService produtoSaidaService;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private ProdutoCapaService produtoCapaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProdutoSaida() {
        ProdutoSaidaPostDTO produtoSaidaDTO = new ProdutoSaidaPostDTO();
        produtoSaidaDTO.setProdutoCapa(new ProdutoCapa());
        produtoSaidaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(true);

        ResponseEntity<Object> response = produtoSaidaController.create(produtoSaidaDTO);

        verify(produtoSaidaService, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Produto cadastrado com sucesso", response.getBody());
    }

    @Test
    public void testCreateProdutoSaidaProdutoCapaNaoEncontrado() {
        ProdutoSaidaPostDTO produtoSaidaDTO = new ProdutoSaidaPostDTO();
        produtoSaidaDTO.setProdutoCapa(new ProdutoCapa());
        produtoSaidaDTO.getProdutoCapa().setId(1L);

        when(produtoCapaService.existById(1L)).thenReturn(false);

        ResponseEntity<Object> response = produtoSaidaController.create(produtoSaidaDTO);

        verifyZeroInteractions(produtoSaidaService);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ProdutoCapa correspondente não encontrado.", response.getBody());
    }

    @Test
    public void testFindAllProdutoSaida() {
        List<ProdutoSaida> produtoSaidas = new ArrayList<>();
        List<ProdutoSaidaGetDTO> produtoSaidaGetDTOs = new ArrayList<>();

        when(produtoSaidaService.findAll()).thenReturn(produtoSaidas);
        when(mapStructMapper.produtoSaidaGetDTOAllToProdutoSaida(produtoSaidas)).thenReturn(produtoSaidaGetDTOs);

        ResponseEntity<List<ProdutoSaidaGetDTO>> response = produtoSaidaController.findAll();

        verify(produtoSaidaService, times(1)).findAll();
        verify(mapStructMapper, times(1)).produtoSaidaGetDTOAllToProdutoSaida(produtoSaidas);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoSaidaGetDTOs, response.getBody());
    }

    @Test
    public void testFindByIdProdutoSaida() {
        Long id = 1L;
        ProdutoSaida produtoSaida = new ProdutoSaida();
        ProdutoSaidaGetDTO produtoSaidaGetDTO = new ProdutoSaidaGetDTO();

        when(produtoSaidaService.findById(id)).thenReturn(Optional.of(produtoSaida));
        when(mapStructMapper.produtoSaidaGetDTOToProdutoSaida(produtoSaida)).thenReturn(produtoSaidaGetDTO);

        ResponseEntity<ProdutoSaidaGetDTO> response = produtoSaidaController.findById(id);

        verify(produtoSaidaService, times(1)).findById(id);
        verify(mapStructMapper, times(1)).produtoSaidaGetDTOToProdutoSaida(produtoSaida);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoSaidaGetDTO, response.getBody());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testFindByIdProdutoSaidaNotFound() {
        Long id = 1L;

        when(produtoSaidaService.findById(id)).thenReturn(Optional.empty());

        produtoSaidaController.findById(id);
    }

    @Test
    public void testDeleteProdutoSaida() {
        Long id = 1L;
        ProdutoSaida produtoSaida = new ProdutoSaida();

        when(produtoSaidaService.findById(id)).thenReturn(Optional.of(produtoSaida));

        ResponseEntity<Object> response = produtoSaidaController.delete(id);

        verify(produtoSaidaService, times(1)).findById(id);
        verify(produtoSaidaService, times(1)).delete(produtoSaida);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto 1 excluído com sucesso", response.getBody());
    }

    @Test
    public void testDeleteProdutoSaidaNotFound() {
        Long id = 1L;

        when(produtoSaidaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoSaidaController.delete(id);

        verify(produtoSaidaService, times(1)).findById(id);
        verifyZeroInteractions(produtoSaidaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto não encontrado", response.getBody());
    }

    @Test
    public void testUpdateProdutoSaida() {
        Long id = 1L;
        ProdutoSaidaPostDTO produtoSaidaDTO = new ProdutoSaidaPostDTO();
        ProdutoSaida produtoSaida = new ProdutoSaida();

        when(produtoSaidaService.findById(id)).thenReturn(Optional.of(produtoSaida));

        ResponseEntity<Object> response = produtoSaidaController.update(id, produtoSaidaDTO);

        verify(produtoSaidaService, times(1)).findById(id);
        verify(produtoSaidaService, times(1)).save(any(ProdutoSaida.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateProdutoSaidaNotFound() {
        Long id = 1L;
        ProdutoSaidaPostDTO produtoSaidaDTO = new ProdutoSaidaPostDTO();

        when(produtoSaidaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoSaidaController.update(id, produtoSaidaDTO);

        verify(produtoSaidaService, times(1)).findById(id);
        verifyZeroInteractions(produtoSaidaService);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fornecedor não encontrado", response.getBody());
    }

    private void verifyZeroInteractions(ProdutoSaidaService produtoSaidaService) {
    }
}
