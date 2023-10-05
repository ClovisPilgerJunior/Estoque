package com.janfer.estoque.controllers.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldMessageTest {

  @org.junit.Before
  public void setUp() throws Exception {
  }

  @Test
  public void test_createFieldMessageWithValidStrings() {
    FieldMessage fieldMessage = new FieldMessage("field", "message");
    assertEquals("field", fieldMessage.getField());
    assertEquals("message", fieldMessage.getMessage());
  }

  @Test
  public void test_createFieldMessageWithNullField() {
    FieldMessage fieldMessage = new FieldMessage(null, "message");
    assertNull(fieldMessage.getField());
    assertEquals("message", fieldMessage.getMessage());
  }
}