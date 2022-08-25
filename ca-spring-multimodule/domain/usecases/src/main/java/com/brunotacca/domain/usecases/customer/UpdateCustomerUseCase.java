package com.brunotacca.domain.usecases.customer;

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
      updatedCustomer = customerFactory.getExistingCustomer(inputDTO.id(), inputDTO.name(), inputDTO.email(), address);
    } catch (BusinessException e) {
      throw new BusinessValidationException(e.getMessage());
    }
    
    // Fetch from data access
    Customer existingCustomer = customerDataAccess.read(updatedCustomer.getId());

    // Verify email changed 
    if(!updatedCustomer.getEmail().equals(existingCustomer.getEmail())) {
      // Validate uniqueness
      Customer customerWithUpdatedMail = customerDataAccess.findByEmail(updatedCustomer.getEmail());
      
      // If there is a customer with this email already
      if(customerWithUpdatedMail!=null) {
        // Error if not unique
        throw new DomainException("There is already a customer with this updated email.");
      }
    }

    // Update Data and Save
    
    // Keep the active status
    if(Boolean.TRUE.equals(existingCustomer.isActive())) 
      updatedCustomer.activate();
    else updatedCustomer.deactivate();

    customerDataAccess.save(updatedCustomer);

    // Output customer
    return customerMapper.outputFromEntity(updatedCustomer);
  }
  
}
