package com.brunotacca.domain.usecases.customer.dto;

import com.brunotacca.domain.entities.customer.Customer;

public class CustomerMapper {

  public CustomerOutputDTO fromCustomer(Customer c) {
    return new CustomerOutputDTO(
      c.getId(), 
      c.getName(), 
      c.getEmail(), 
      c.isActive(), 
      c.getAddress().getStreet(), 
      c.getAddress().getNumber(), 
      c.getAddress().getCity(), 
      c.getAddress().getZip()
    );
  }

}
