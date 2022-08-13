package com.brunotacca.adapter.controllers;

import com.brunotacca.domain.usecases.customer.CustomerUseCaseFactory;
import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

public class CustomerController {

  private final UseCase<CreateCustomerInputDTO, CustomerOutputDTO> createCustomerUseCase;

  public CustomerController(CustomerUseCaseFactory customerUseCaseFactory) {
    createCustomerUseCase = customerUseCaseFactory.getCreateCustomerUseCase();
  }

  public CustomerOutputDTO createCustomer(CreateCustomerInputDTO inputDTO) throws DomainException {
    return createCustomerUseCase.execute(inputDTO);
  }

}
