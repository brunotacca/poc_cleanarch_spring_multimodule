package com.brunotacca.domain.usecases.customer;

import java.util.Optional;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCustomerByIdUseCase implements UseCase<CustomerIdDTO, CustomerOutputDTO> {

  private final CustomerDataAccess customerDataAccess;
  private final CustomerMapper customerMapper;

  @Override
  public CustomerOutputDTO execute(CustomerIdDTO inputDTO) throws DomainException {
    // Validate Input
    if(inputDTO.id()==null) {
      throw new DomainException("Customer Id is required.");
    }

    Optional<Customer> customer = customerDataAccess.read(inputDTO.id());

    if(!customer.isPresent()) {
      throw new DomainException("Customer not found.");
    }

    // Convert respose to output and return
    return customerMapper.outputFromEntity(customer.get());
  }
  
}
