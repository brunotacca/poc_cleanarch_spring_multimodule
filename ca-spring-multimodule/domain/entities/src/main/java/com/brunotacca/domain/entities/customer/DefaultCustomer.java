package com.brunotacca.domain.entities.customer;


import com.brunotacca.domain.entities.shared.utils.UtilsFactory;
import com.brunotacca.domain.entities.shared.utils.ValidationUtils;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class DefaultCustomer implements Customer {

  private final UUID id;

  @With(AccessLevel.PRIVATE)
  private final String name;

  @With(AccessLevel.PRIVATE)
  private final String email;

  @With(AccessLevel.PRIVATE)
  private final Address address;

  @With(AccessLevel.PRIVATE)
  private final Boolean active;

  private final ValidationUtils validationUtils;

  protected DefaultCustomer(UUID id, String name, String email, Address address) throws BusinessException {
    this.validationUtils = (new UtilsFactory()).getValidationUtils();

    this.id = id;
    this.name = name;
    this.email = email;
    this.address = address;
    this.active = false;
    this.validate();
  }

  @Override
  public void validate() throws BusinessException {
    if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
    if(this.validationUtils.isNullOrEmpty(this.name)) throw new RequiredFieldException("name");
    if(this.validationUtils.isNullOrEmpty(this.email)) throw new RequiredFieldException("email");
    if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
    this.address.validate();
  }

  @Override
  public UUID getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  @Override
  public Address getAddress() {
    return this.address;
  }

  @Override
  public Boolean isActive() {
    return this.active;
  }

  @Override
  public Customer changeName(String newName) throws BusinessException {
    DefaultCustomer c = this.name.equals(newName) ? this : this.withName(newName);
    c.validate();
    return c;
  }

  @Override
  public Customer changeEmail(String newEmail) throws BusinessException {
    DefaultCustomer c = this.email.equals(newEmail) ? this : this.withEmail(newEmail);
    c.validate();
    return c;
  }

  @Override
  public Customer changeAddress(Address newAddr) throws BusinessException {
    DefaultCustomer c = this.address.equals(newAddr) ? this : this.withAddress(newAddr); 
    c.validate();
    return c;
  }

  @Override
  public Customer activate() {
    return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
  }

  @Override
  public Customer deactivate() {
    return this.active.equals(Boolean.FALSE) ? this : this.withActive(false);
    
  }

  
}
