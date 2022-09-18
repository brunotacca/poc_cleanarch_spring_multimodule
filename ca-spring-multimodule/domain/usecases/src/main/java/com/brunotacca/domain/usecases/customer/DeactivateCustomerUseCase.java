package com.brunotacca.domain.usecases.customer;

import java.util.Optional;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCaseOnlyInput;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class DeactivateCustomerUseCase implements UseCaseOnlyInput<CustomerIdDTO> {

  private final CustomerDataAccess customerDataAccess;

  @Override
  public void execute(CustomerIdDTO inputDTO) throws DomainException {

    if(inputDTO.id()==null) {
      throw new DomainException("Customer Id is required.");
    }

    Optional<Customer> customer = customerDataAccess.read(inputDTO.id());

    if(!customer.isPresent()) {
      throw new DomainException("Customer not found.");
    }

    if(Boolean.TRUE.equals(customer.get().isActive())) {
      customer.get().deactivate();
      customerDataAccess.update(customer.get());
    }

  }
  
}
