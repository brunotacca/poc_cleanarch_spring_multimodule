package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class ActivateCustomerUseCaseTest {
  
  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private ActivateCustomerUseCase activateCustomerUseCase = new ActivateCustomerUseCase(customerDataAccessMock);

  private final Customer customerMock = mock(Customer.class);

  private final String validId = "e3119506-030a-4877-a219-389ef21118a4";
  private CustomerIdDTO validInputDTO = new CustomerIdDTO(validId);

  void prepareDefaultStubs() throws BusinessException, DataAccessException {
    doReturn(customerMock).when(customerDataAccessMock).read(any());
  }

  @Test
  void shoudlCallDataAccessCorrectly() throws BusinessException, DomainException {
    // Activating
    prepareDefaultStubs();
    doReturn(false).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(1)).save(any());

    // Activation not needed
    reset(customerDataAccessMock);
    prepareDefaultStubs();
    doReturn(true).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(0)).save(any());
  }

  @Test
  void shouldActivateCustomer() throws BusinessException, DomainException {
    // Activating
    prepareDefaultStubs();
    doReturn(false).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerMock, times(1)).activate();
    verify(customerDataAccessMock, times(1)).save(any());
  }

  @Nested
  class ActivatingCustomerShouldThrow {

    @Test
    void whenIdIsNull() throws DomainException, BusinessException {
      prepareDefaultStubs();
      assertThrows(DomainException.class, () -> activateCustomerUseCase.execute(new CustomerIdDTO(null)));
      assertThrows(DomainException.class, () -> activateCustomerUseCase.execute(new CustomerIdDTO("")));
    }

    @Test
    void whenCustomerNotFound() throws DomainException, BusinessException {
      doReturn(null).when(customerDataAccessMock).read(any());
      assertThrows(DomainException.class, () -> activateCustomerUseCase.execute(validInputDTO));
    }

  }

}
