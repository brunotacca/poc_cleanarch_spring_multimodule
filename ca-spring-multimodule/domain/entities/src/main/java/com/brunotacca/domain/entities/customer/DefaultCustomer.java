package com.brunotacca.domain.entities.customer;


import static com.brunotacca.domain.entities.shared.utils.ValidationUtils.*;

import com.brunotacca.domain.entities.shared.entities.BaseEntity;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class DefaultCustomer extends BaseEntity implements Customer {

  private final String id;

  @With(AccessLevel.PRIVATE)
  private final String name;

  @With(AccessLevel.PRIVATE)
  private final String email;

  @With(AccessLevel.PRIVATE)
  private final Address address;

  @With(AccessLevel.PRIVATE)
  private final Boolean active;

  protected DefaultCustomer(String id, String name, String email, Address address) throws BusinessException {
    this.id = id;
    this.name = name;
    this.email = email;
    this.address = address;
    this.active = false;
    this.validate();
  }

  @Override
  public void validate() throws BusinessException {
    if(isNull(this.id)) throw new RequiredFieldException("id");
    if(isNullOrEmpty(this.name)) throw new RequiredFieldException("name");
    if(isNullOrEmpty(this.email)) throw new RequiredFieldException("email");
    if(isNull(this.address)) throw new RequiredFieldException("address");
    this.address.validate();
    if(isNull(this.active)) throw new RequiredFieldException("active");
  }

  @Override
  public String getId() {
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
