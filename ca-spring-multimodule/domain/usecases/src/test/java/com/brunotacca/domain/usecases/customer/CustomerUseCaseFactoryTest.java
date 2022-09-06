package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerNameInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.UseCaseOnlyInput;

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

  @Test
  void shouldReturnFindCustomerByNameUseCase() {
    UseCase<CustomerNameInputDTO, List<CustomerOutputDTO>> findByUseCase = customerUseCaseFactory.getFindCustomerByNameUseCase();

    assertNotNull(findByUseCase);
    assertEquals(FindCustomerByNameUseCase.class, findByUseCase.getClass());
  }

  @Test
  void shouldReturnGetCustomerByIdUseCase() {
    UseCase<CustomerIdDTO, CustomerOutputDTO> getUseCase = customerUseCaseFactory.getGetCustomerByIdUseCase();

    assertNotNull(getUseCase);
    assertEquals(GetCustomerByIdUseCase.class, getUseCase.getClass());
  }

  @Test
  void shouldReturnActivateCustomerUseCase() {
    UseCaseOnlyInput<CustomerIdDTO> activateUseCase = customerUseCaseFactory.getActivateCustomerUseCase();

    assertNotNull(activateUseCase);
    assertEquals(ActivateCustomerUseCase.class, activateUseCase.getClass());
  }

  @Test
  void shouldReturnDeactivateCustomerUseCase() {
    UseCaseOnlyInput<CustomerIdDTO> deactivateUseCase = customerUseCaseFactory.getDeactivateCustomerUseCase();

    assertNotNull(deactivateUseCase);
    assertEquals(DeactivateCustomerUseCase.class, deactivateUseCase.getClass());
  }

}
