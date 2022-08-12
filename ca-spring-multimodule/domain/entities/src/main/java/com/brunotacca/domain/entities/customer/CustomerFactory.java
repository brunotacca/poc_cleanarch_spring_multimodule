package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

public class CustomerFactory {

  public Customer createCustomer(String name, String email, Address address) throws BusinessException {
    final Customer customer = new DefaultCustomer(UUID.randomUUID().toString(), name, email, address);
    return customer;
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

    final Customer customer = new DefaultCustomer(uuid.toString(), name, email, address);
    return customer;
  }

  public Address createAddress(String street, String number, String zip, String city) throws BusinessException {
    final Address address = new Address(street, number, zip, city);
    return address;
  }

}
