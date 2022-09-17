package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

public class CustomerFactory {

  public Customer createCustomer(String name, String email, Address address) throws BusinessException {
    return new DefaultCustomer(UUID.randomUUID(), name, email, address);
  }

  public Customer recreateExistingCustomer(UUID id, String name, String email, Boolean active, Address address) throws BusinessException {
    if (id == null) {
      throw new RequiredFieldException("id");
    }

    Customer existingCustomer = new DefaultCustomer(id, name, email, address);

    return keepActiveValueForExistingCustomer(existingCustomer, active);
  }

  Customer keepActiveValueForExistingCustomer(Customer existingCustomer, Boolean activeValue) {
    if(activeValue!=null) {
      if(Boolean.TRUE.equals(activeValue)) return existingCustomer.activate();
      else return existingCustomer.deactivate();
    }
    return null;
  }

  public Address createAddress(String street, String number, String zip, String city) throws BusinessException {
    return new Address(street, number, zip, city);
  }

}
