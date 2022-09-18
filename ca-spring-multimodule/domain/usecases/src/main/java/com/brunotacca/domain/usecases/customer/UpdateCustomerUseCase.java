package com.brunotacca.domain.usecases.customer;

import java.util.Optional;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.BusinessValidationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateCustomerUseCase implements UseCase<UpdateCustomerInputDTO, CustomerOutputDTO> {

  private final CustomerDataAccess customerDataAccess;
  private final CustomerFactory customerFactory;
  private final CustomerMapper customerMapper;

  @Override
  public CustomerOutputDTO execute(UpdateCustomerInputDTO inputDTO) throws DomainException {

    // Validate Input
    Address address;
    Customer updatedCustomer;
    try {
      // Validations should throw
      address = customerFactory.createAddress(inputDTO.street(), inputDTO.number(), inputDTO.zip(), inputDTO.city());
      updatedCustomer = customerFactory.recreateExistingCustomer(inputDTO.id(), inputDTO.name(), inputDTO.email(), null, address);
    } catch (BusinessException e) {
      throw new BusinessValidationException(e.getMessage());
    }
    
    // Fetch from data access
    Optional<Customer> existingCustomer = customerDataAccess.read(updatedCustomer.getId());

    if(!existingCustomer.isPresent()) {
      throw new DomainException("Customer not found.");
    }

    // Verify email changed 
    if(!updatedCustomer.getEmail().equals(existingCustomer.get().getEmail())) {
      // Validate uniqueness
      Optional<Customer> customerWithUpdatedMail = customerDataAccess.findByEmail(updatedCustomer.getEmail());
      
      // If there is a customer with this email already
      if(customerWithUpdatedMail.isPresent()) {
        // Error if not unique
        throw new DomainException("There is already a customer with this updated email.");
      }
    }

    // Update Data and Save
    
    // Keep the active status
    if(Boolean.TRUE.equals(existingCustomer.get().isActive())) 
      updatedCustomer.activate();
    else updatedCustomer.deactivate();

    customerDataAccess.update(updatedCustomer);

    // Output customer
    return customerMapper.outputFromEntity(updatedCustomer);
  }
  
}
