package com.brunotacca.domain.entities.shared.entities;

import com.brunotacca.domain.entities.shared.exceptions.DomainException;

public abstract class BaseValueObject {

  public abstract void validate() throws DomainException;
  
}
