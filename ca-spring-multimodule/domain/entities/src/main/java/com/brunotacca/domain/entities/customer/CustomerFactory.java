package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public class CustomerFactory {

  public static Customer create(String name, String email, Address address) throws BusinessException {
    final Customer customer = new TheCustomer(UUID.randomUUID().toString(), name, email, address);
    return customer;
  }
  
}
