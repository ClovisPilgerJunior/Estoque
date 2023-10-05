package com.janfer.estoque.controllers.exceptions;

import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceExceptionHandlerTest {

  @Test
  public void test_response_entity_with_not_found_status() {
    ObjectNotFoundException exception = new ObjectNotFoundException("Object not found");
    HttpServletRequest request = new MockHttpServletRequest();

    ResourceExceptionHandler handler = new ResourceExceptionHandler();
    ResponseEntity<StandardError> response = handler.objectNotFoundException(exception, request);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Object Not Found", response.getBody().getError());
    assertEquals("Object not found", response.getBody().getMessage());
    assertEquals(request.getRequestURI(), response.getBody().getPath());
  }

  @Test
  public void validationErrors() {
  }

  @Test
  public void constraintViolationException() {
  }

  @Test
  public void productDisableException() {
  }
}