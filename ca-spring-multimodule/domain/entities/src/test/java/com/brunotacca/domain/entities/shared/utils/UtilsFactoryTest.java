package com.brunotacca.domain.entities.shared.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class UtilsFactoryTest {

  private UtilsFactory utilsFactory;

  @BeforeEach
  void beforeEach() {
    this.utilsFactory = new UtilsFactory();
  }

  @Test
  void shouldReturnValidationUtils() {
    ValidationUtils validationUtils = this.utilsFactory.getValidationUtils();
    assertNotNull(validationUtils);
  }
}
