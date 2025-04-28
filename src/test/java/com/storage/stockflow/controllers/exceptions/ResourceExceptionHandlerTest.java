package com.storage.stockflow.controllers.exceptions;

import com.storage.stockflow.services.exceptions.ConstraintViolationException;
import com.storage.stockflow.services.exceptions.DataIntegrityViolationException;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import com.storage.stockflow.services.exceptions.ProductDisableException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    @Mock
    private ObjectNotFoundException objectNotFoundException;

    @Mock
    private DataIntegrityViolationException dataIntegrityViolationException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @Mock
    private ProductDisableException productDisableException;

    @Mock
    private HttpServletRequest request;

    @Before

    public void setUp() {
        methodArgumentNotValidException = Mockito.mock(MethodArgumentNotValidException.class);
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void testObjectNotFoundException() {
        when(objectNotFoundException.getMessage()).thenReturn("Object Not Found");

        ResponseEntity<StandardError> response = resourceExceptionHandler.objectNotFoundException(objectNotFoundException, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Object Not Found", Objects.requireNonNull(response.getBody()).getMessage());
        verify(objectNotFoundException, times(1)).getMessage();
    }

    @Test
    public void testDataIntegrityViolationException() {
        when(dataIntegrityViolationException.getMessage()).thenReturn("Violation of data integrity");

        ResponseEntity<StandardError> response = resourceExceptionHandler.dataIntegrityViolationException(dataIntegrityViolationException, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Violation of data integrity", Objects.requireNonNull(response.getBody()).getMessage());
        verify(dataIntegrityViolationException, times(1)).getMessage();
    }

    @Test
    public void testValidationErrors() {
        // Crie uma lista de erros de validação simulados
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("object", "field1", "Error 1"));
        fieldErrors.add(new FieldError("object", "field2", "Error 2"));

        // Crie um objeto BindingResult simulado que contém os erros de validação
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Configure a exceção MethodArgumentNotValidException com o BindingResult simulado
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        // Crie uma instância de ResourceExceptionHandler
        ResourceExceptionHandler resourceExceptionHandler = new ResourceExceptionHandler();

        // Chame o método a ser testado
        ResponseEntity<StandardError> response = resourceExceptionHandler.validationErrors(methodArgumentNotValidException, request);

        // Verifique se o status da resposta é HttpStatus.BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verifique se a mensagem de erro contém os erros de validação esperados
        String errorMessage = Objects.requireNonNull(response.getBody()).getMessage();
        assertEquals("Validation error in field", errorMessage);
    }


    @Test
    public void testConstraintViolationException() {
        when(constraintViolationException.getMessage()).thenReturn("Invalid Brazilian Individual Taxpayer Registration (CPF) number");

        ResponseEntity<StandardError> response = resourceExceptionHandler.constraintViolationException(constraintViolationException, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Brazilian Individual Taxpayer Registration (CPF) number", Objects.requireNonNull(response.getBody()).getMessage());
        verify(constraintViolationException, times(1)).getMessage();
    }

    @Test
    public void testProductDisableException() {
        when(productDisableException.getMessage()).thenReturn("Produto está desativado no sistema");

        ResponseEntity<StandardError> response = resourceExceptionHandler.productDisableException(productDisableException, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Produto está desativado no sistema", Objects.requireNonNull(response.getBody()).getMessage());
        verify(productDisableException, times(1)).getMessage();
    }
}
