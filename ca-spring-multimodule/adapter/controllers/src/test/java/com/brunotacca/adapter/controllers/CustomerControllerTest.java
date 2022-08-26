package com.brunotacca.adapter.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.customer.CustomerUseCaseFactory;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.UseCase;
import com.brunotacca.domain.usecases.shared.UseCaseOnlyInput;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerControllerTest {

  private CustomerController customerController;
  private final CustomerUseCaseFactory customerUseCaseFactoryMock = mock(CustomerUseCaseFactory.class);
  
  private final UseCase<?, ?> useCaseMock = mock(UseCase.class);
  private final UseCaseOnlyInput<?> useCaseOnlyInputMock = mock(UseCaseOnlyInput.class);
  private final CustomerOutputDTO customerOutputMock = new CustomerOutputDTO("id", "name", "email", false, "street", "number", "city", "zip");

  @BeforeEach
  void beforeEach() {
    doReturn(useCaseMock).when(customerUseCaseFactoryMock).getCreateCustomerUseCase();
    doReturn(useCaseMock).when(customerUseCaseFactoryMock).getUpdateCustomerUseCase();
    doReturn(useCaseMock).when(customerUseCaseFactoryMock).getFindCustomerByNameUseCase();
    doReturn(useCaseOnlyInputMock).when(customerUseCaseFactoryMock).getActivateCustomerUseCase();
    doReturn(useCaseOnlyInputMock).when(customerUseCaseFactoryMock).getDeactivateCustomerUseCase();
    this.customerController = new CustomerController(customerUseCaseFactoryMock);
  }
  
  @Test
  void shouldCallUseCasesCorrectlyCreate() throws DomainException {
    customerController.createCustomer(null);
    verify(useCaseMock, times(1)).execute(any());
  }

  @Test
  void shouldCallUseCasesCorrectlyUpdate() throws DomainException {
    customerController.updateCustomer(null);
    verify(useCaseMock, times(1)).execute(any());
  }

  @Test
  void shouldCallUseCasesCorrectlyFind() throws DomainException {
    customerController.findCustomer(null);
    verify(useCaseMock, times(1)).execute(any());
  }


  @Test
  void shouldCallUseCasesCorrectlyActivate() throws DomainException {
    customerController.activateCustomer(null);
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }

  @Test
  void shouldCallUseCasesCorrectlyDeactivate() throws DomainException {
    customerController.deactivateCustomer(null);
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }

  @Test
  void shouldCallUseCasesCorrectlyCreateActive() throws DomainException {
    doReturn(customerOutputMock).when(useCaseMock).execute(any());

    customerController.createActiveCustomer(null);
    verify(useCaseMock, times(1)).execute(any());
    verify(useCaseOnlyInputMock, times(1)).execute(any());
  }

  @Test
  void shouldReturnCustomerActivated() throws DomainException {
    doReturn(customerOutputMock).when(useCaseMock).execute(any());
    CustomerOutputDTO actual = customerController.createActiveCustomer(null);
    assertTrue(actual.active());
  }
}
