package com.janfer.estoque.controllers.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldMessageTest {

  @Test
  public void testFieldMessageConstructorAndGetters() {
    String field = "fieldName";
    String message = "Test message";

    FieldMessage fieldMessage = new FieldMessage(field, message);

    // Verifying if the constructor correctly initializes the object
    assertEquals(field, fieldMessage.getField());
    assertEquals(message, fieldMessage.getMessage());
  }

  @Test
  public void testFieldMessageSetters() {
    FieldMessage fieldMessage = new FieldMessage();

    // Using setters to set the field and message
    String field = "fieldName";
    String message = "Test message";

    fieldMessage.setField(field);
    fieldMessage.setMessage(message);

    // Verifying if the setters correctly set the values
    assertEquals(field, fieldMessage.getField());
    assertEquals(message, fieldMessage.getMessage());
  }
}