package com.brunotacca.domain.usecases.customer;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCaseOnlyInput;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ActivateCustomerUseCase implements UseCaseOnlyInput<CustomerIdDTO> {

  private final CustomerDataAccess customerDataAccess;

  @Override
  public void execute(CustomerIdDTO inputDTO) throws DomainException {

    if(inputDTO.id()==null || inputDTO.id().isBlank()) {
      throw new DomainException("Customer Id is required.");
    }

    Customer c = customerDataAccess.read(inputDTO.id());

    if(c==null) {
      throw new DomainException("Customer not found.");
    }

    if(Boolean.FALSE.equals(c.isActive())) {
      c.activate();
      customerDataAccess.save(c);
    }

  }
  
}
