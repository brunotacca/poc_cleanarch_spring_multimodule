package com.brunotacca.external.apis.rest.customer;

import org.springframework.stereotype.Component;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;

@Component
public class CustomerModelMapper {

  public CreateCustomerInputDTO inputFromModel(NewCustomerModel c) {
    return new CreateCustomerInputDTO(
      c.getName(), 
      c.getEmail(), 
      c.getStreet(), 
      c.getNumber(), 
      c.getCity(), 
      c.getZip()
    );
  }

  public CustomerOutputDTO outputFromModel(CustomerModel c) {
    return new CustomerOutputDTO(
      c.getId(), 
      c.getName(), 
      c.getEmail(), 
      c.getActive(), 
      c.getStreet(), 
      c.getNumber(), 
      c.getCity(), 
      c.getZip()
    );
  }
  
  public CustomerModel modelFromOutput(CustomerOutputDTO c) {
    return new CustomerModel(
      c.id(),
      c.name(), 
      c.email(), 
      c.active(), 
      c.street(), 
      c.number(), 
      c.city(), 
      c.zip()
    );
  }
  

  
}
