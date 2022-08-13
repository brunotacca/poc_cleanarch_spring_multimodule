package com.brunotacca.domain.entities.shared.exceptions.causes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class RequiredFieldExceptionTest {

  @Test
  void shouldGenerateCorrectMessage() {
    String fieldName = "MyField";

    Exception exc = new RequiredFieldException(fieldName);

    String actualMessage = exc.getMessage();
    String expectedMessage = String.format(RequiredFieldException.PATTERN, fieldName);

    assertEquals(actualMessage, expectedMessage);
  }
}
