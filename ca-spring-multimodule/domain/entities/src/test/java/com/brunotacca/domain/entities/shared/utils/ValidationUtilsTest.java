package com.brunotacca.domain.entities.shared.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
class ValidationUtilsTest {

  private ValidationUtils validationUtils;

  @BeforeEach
  void beforeEach() {
    this.validationUtils = new ValidationUtils();
  }

  @Test
  void testIsNull() {
    assertTrue(this.validationUtils.isNull(null));
    assertFalse(this.validationUtils.isNull(new String()));
  }

  @Test
  void testIsNullOrEmptyCollection() {
    Collection<String> collection = null;
    assertTrue(this.validationUtils.isNullOrEmpty(collection));
    collection = new ArrayList<String>();
    assertTrue(this.validationUtils.isNullOrEmpty(collection));
    collection.add("1");
    assertFalse(this.validationUtils.isNullOrEmpty(collection));
  }

  @Test
  void testIsNullOrEmptyString() {
    String string = null;
    assertTrue(this.validationUtils.isNullOrEmpty(string));
    string = "";
    assertTrue(this.validationUtils.isNullOrEmpty(string));
    string = "   ";
    assertTrue(this.validationUtils.isNullOrEmpty(string));
    string = "1";
    assertFalse(this.validationUtils.isNullOrEmpty(string));
  }
}
