package com.brunotacca.domain.usecases.customer;

import java.util.List;
import java.util.stream.Collectors;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerNameInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FindCustomerByNameUseCase implements UseCase<CustomerNameInputDTO, List<CustomerOutputDTO>> {

  private final CustomerDataAccess customerDataAccess;
  private final CustomerMapper customerMapper;

  @Override
  public List<CustomerOutputDTO> execute(CustomerNameInputDTO inputDTO) throws DomainException {
    // Validate Input
    if(inputDTO.name()==null || inputDTO.name().trim().isBlank() || inputDTO.name().trim().length()<3) {
      throw new DomainException("Customer Name is required (At least 3 char).");
    }

    List<Customer> listCustomers = customerDataAccess.findByName(inputDTO.name().trim());

    // Convert respose to output and return
    return listCustomers.stream()
      .map(customerMapper::outputFromEntity)
      .toList();
  }
  
}
