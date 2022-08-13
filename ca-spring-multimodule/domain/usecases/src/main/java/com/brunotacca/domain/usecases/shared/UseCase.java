package com.brunotacca.domain.usecases.shared;

import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public interface UseCase {
  
  public void execute() throws DomainException;

  public static interface OnlyInput<I> {
    public abstract void execute(I inputDTO) throws DomainException;
  }
  
  public static interface InputOutput<I,O> {
    public abstract O execute(I inputDTO) throws DomainException;
  }
  
  public static interface OnlyOutput<O> {
    public abstract O execute() throws DomainException;
  }
  
}