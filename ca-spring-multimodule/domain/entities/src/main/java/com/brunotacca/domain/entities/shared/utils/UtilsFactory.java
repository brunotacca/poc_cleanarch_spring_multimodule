package com.brunotacca.domain.entities.shared.utils;

public class UtilsFactory {

  private final ValidationUtils validationUtils = new ValidationUtils();

  public ValidationUtils getValidationUtils() {
    return validationUtils;
  }
  
}
