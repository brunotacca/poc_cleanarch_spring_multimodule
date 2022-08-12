package com.brunotacca.domain.usecases.shared.exceptions.causes;

import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public class BusinessValidationException extends DomainException {

  public BusinessValidationException(String message) {
    super(message);
  }
  
}
