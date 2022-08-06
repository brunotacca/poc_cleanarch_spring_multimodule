package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public class CustomerFactory {

  public static Customer createCustomer(String name, String email, Address address) throws BusinessException {
    final Customer customer = new TheCustomer(UUID.randomUUID().toString(), name, email, address);
    return customer;
  }
  
  public static Address createAddress(String street, String number, String zip, String city) throws BusinessException {
    final Address address = new Address(street, number, zip, city);
    return address;
  }
  
}
