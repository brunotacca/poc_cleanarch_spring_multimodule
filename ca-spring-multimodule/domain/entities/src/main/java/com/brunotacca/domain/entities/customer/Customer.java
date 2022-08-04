package com.brunotacca.domain.entities.customer;

import com.brunotacca.domain.entities.shared.exceptions.DomainException;

public interface Customer {
  
  public String getId();
  public String getName();
  public String getEmail();
  public Address getAddress();
  public Boolean isActive();
  
  public Customer changeName(String newName) throws DomainException;
  public Customer changeEmail(String newEmail) throws DomainException;
  public Customer changeAddress(Address newAddr) throws DomainException;
  public Customer activate();
  public Customer deactivate();

}
