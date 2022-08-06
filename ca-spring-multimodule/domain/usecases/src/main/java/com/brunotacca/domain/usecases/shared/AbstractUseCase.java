package com.brunotacca.domain.usecases.shared;

public abstract class AbstractUseCase {
  
  public abstract void execute();

  public static abstract class WithParam<P> {
    public abstract void execute(P param);
  }
  
  public static abstract class WithParamWithReturn<P,R> {
    public abstract R execute(P param);
  }
  
  public static abstract class WithReturn<R> {
    public abstract R execute();
  }
  
}