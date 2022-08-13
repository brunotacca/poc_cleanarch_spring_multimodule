package com.brunotacca.domain.usecases.shared;

import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public interface UseCaseOnlyOutput<O> {
  
  public O execute() throws DomainException;
  
}