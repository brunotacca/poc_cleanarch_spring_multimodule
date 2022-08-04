package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.DomainException;

public class CustomerFactory {

  public static Customer create(String name, String email, Address address) throws DomainException {
    final Customer customer = new TheCustomer(UUID.randomUUID().toString(), name, email, address);
    return customer;
  }
  
}
