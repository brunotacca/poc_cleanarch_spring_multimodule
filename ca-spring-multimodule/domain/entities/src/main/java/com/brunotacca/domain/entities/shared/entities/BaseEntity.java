package com.brunotacca.domain.entities.shared.entities;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public abstract class BaseEntity {

  public abstract void validate() throws BusinessException;
  
}
