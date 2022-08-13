package com.brunotacca.domain.entities.customer;

import com.brunotacca.domain.entities.shared.entities.BaseEntity;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public interface Customer extends BaseEntity {
  
  public String getId();
  public String getName();
  public String getEmail();
  public Address getAddress();
  public Boolean isActive();
  
  public Customer changeName(String newName) throws BusinessException;
  public Customer changeEmail(String newEmail) throws BusinessException;
  public Customer changeAddress(Address newAddr) throws BusinessException;
  public Customer activate();
  public Customer deactivate();

}
