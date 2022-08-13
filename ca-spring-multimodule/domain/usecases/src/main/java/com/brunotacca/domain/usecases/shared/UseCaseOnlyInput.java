package com.brunotacca.domain.usecases.shared;

import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public interface UseCaseOnlyInput<I> {
  
  public void execute(I inputDTO) throws DomainException;
  
}