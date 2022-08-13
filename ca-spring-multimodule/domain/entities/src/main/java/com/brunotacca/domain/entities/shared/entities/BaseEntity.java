package com.brunotacca.domain.entities.shared.entities;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public interface BaseEntity {

  public void validate() throws BusinessException;
  
}
