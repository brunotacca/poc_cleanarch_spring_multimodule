package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

public class CustomerFactory {

  public Customer createCustomer(String name, String email, Address address) throws BusinessException {
    return new DefaultCustomer(UUID.randomUUID().toString(), name, email, address);
  }

  public Customer getExistingCustomer(String id, String name, String email, Address address) throws BusinessException {
    UUID uuid = null;
    if (id == null || id.trim().isEmpty()) {
      throw new RequiredFieldException("id");
    }

    try {
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new RequiredFieldException("id");
    }

    return new DefaultCustomer(uuid.toString(), name, email, address);
  }

  public Address createAddress(String street, String number, String zip, String city) throws BusinessException {
    return new Address(street, number, zip, city);
  }

}
