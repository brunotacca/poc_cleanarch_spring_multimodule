package com.brunotacca.domain.usecases.customer;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.BusinessValidationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateCustomerUseCase extends UseCase.InputOutput<CreateCustomerInputDTO, CustomerOutputDTO> {

  private final CustomerDataAccess customerDataAccess;
  private final CustomerFactory customerFactory;
  private final CustomerMapper customerMapper;

  @Override
  public CustomerOutputDTO execute(CreateCustomerInputDTO inputDTO) throws DomainException {
    // Validate Input
    Address address;
    Customer customer;
    try {
      // Validations should throw
      address = customerFactory.createAddress(inputDTO.street(), inputDTO.number(), inputDTO.zip(), inputDTO.city());
      customer = customerFactory.createCustomer(inputDTO.name(), inputDTO.email(), address);
    } catch (BusinessException e) {
      throw new BusinessValidationException(e.getMessage());
    }

    // Validate Unique Email
    Customer customerWithEmail = customerDataAccess.findByEmail(customer.getEmail());
    if(customerWithEmail!=null) {
      throw new DomainException("There is already a customer with this email.");
    }

    // Create
    customerDataAccess.save(customer);

    // Convert respose to output
    CustomerOutputDTO outputDTO = customerMapper.fromCustomer(customer);
    return outputDTO;
  }
  
}
