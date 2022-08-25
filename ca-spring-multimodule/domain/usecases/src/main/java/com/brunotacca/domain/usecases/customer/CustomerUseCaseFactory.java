package com.brunotacca.domain.usecases.customer;

import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerUseCaseFactory {

  private final CustomerDataAccess customerDataAccess;
  private final CustomerFactory customerFactory = new CustomerFactory();
  private final CustomerMapper customerMapper = new CustomerMapper();
  
  public UseCase<CreateCustomerInputDTO, CustomerOutputDTO> getCreateCustomerUseCase() {
    return new CreateCustomerUseCase(customerDataAccess, customerFactory, customerMapper);
  }

  public UseCase<UpdateCustomerInputDTO, CustomerOutputDTO> getUpdateCustomerUseCase() {
    return new UpdateCustomerUseCase(customerDataAccess, customerFactory, customerMapper);
  }

}
