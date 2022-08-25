package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;

class CustomerUseCaseFactoryTest {

  private CustomerUseCaseFactory customerUseCaseFactory;
  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);

  @BeforeEach
  void beforeEach() {
    this.customerUseCaseFactory = new CustomerUseCaseFactory(customerDataAccessMock);
  }

  @Test
  void shouldReturnCreateCustomerUseCase() {
    UseCase<CreateCustomerInputDTO, CustomerOutputDTO> createUseCase = customerUseCaseFactory.getCreateCustomerUseCase();

    assertNotNull(createUseCase);
    assertEquals(CreateCustomerUseCase.class, createUseCase.getClass());
  }

  @Test
  void shouldReturnUpdateCustomerUseCase() {
    UseCase<UpdateCustomerInputDTO, CustomerOutputDTO> updateUseCase = customerUseCaseFactory.getUpdateCustomerUseCase();

    assertNotNull(updateUseCase);
    assertEquals(UpdateCustomerUseCase.class, updateUseCase.getClass());
  }

}
