package com.janfer.estoque.controllers.exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FieldMessageTest {

  @Test
  public void testConstructorAndGetters() {
    // Crie um objeto FieldMessage
    FieldMessage fieldMessage = new FieldMessage("fieldName", "error message");

    // Verifique se os valores foram corretamente definidos no construtor
    assertEquals("fieldName", fieldMessage.getField());
    assertEquals("error message", fieldMessage.getMessage());
  }

  @Test
  public void testSetters() {
    // Crie um objeto FieldMessage
    FieldMessage fieldMessage = new FieldMessage();

    // Defina os valores usando os setters
    fieldMessage.setField("fieldName");
    fieldMessage.setMessage("error message");

    // Verifique se os valores foram corretamente definidos pelos setters
    assertEquals("fieldName", fieldMessage.getField());
    assertEquals("error message", fieldMessage.getMessage());
  }
}