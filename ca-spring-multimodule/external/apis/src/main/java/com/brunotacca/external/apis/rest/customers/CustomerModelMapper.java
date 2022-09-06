package com.brunotacca.external.apis.rest.customers;

import org.springframework.stereotype.Component;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;

@Component
class CustomerModelMapper {

  public CreateCustomerInputDTO createDtoFromModel(CustomerModel c) {
    return new CreateCustomerInputDTO(
      c.getName(), 
      c.getEmail(), 
      c.getStreet(), 
      c.getNumber(), 
      c.getCity(), 
      c.getZip()
    );
  }

  public UpdateCustomerInputDTO updateDtoFromModel(CustomerModel c, String id) {
    return new UpdateCustomerInputDTO(
      id,
      c.getName(), 
      c.getEmail(), 
      c.getStreet(), 
      c.getNumber(), 
      c.getCity(), 
      c.getZip()
    );
  }

  public CustomerOutputDTO outputDtoFromModel(ExistingCustomerModel c) {
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
  
  public ExistingCustomerModel modelFromOutput(CustomerOutputDTO c) {
    return new ExistingCustomerModel(
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
