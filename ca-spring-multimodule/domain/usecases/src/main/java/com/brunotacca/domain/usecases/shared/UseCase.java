package com.brunotacca.domain.usecases.shared;

import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public abstract class UseCase {
  
  public abstract void execute() throws DomainException;

  public static abstract class OnlyInput<INPUT> {
    public abstract void execute(INPUT inputDTO) throws DomainException;
  }
  
  public static abstract class InputOutput<INPUT,OUTPUT> {
    public abstract OUTPUT execute(INPUT inputDTO) throws DomainException;
  }
  
  public static abstract class OnlyOutput<OUTPUT> {
    public abstract OUTPUT execute() throws DomainException;
  }
  
}