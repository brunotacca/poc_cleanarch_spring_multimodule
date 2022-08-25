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
class DeactivateCustomerUseCaseTest {
  
  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private DeactivateCustomerUseCase deactivateCustomerUseCase = new DeactivateCustomerUseCase(customerDataAccessMock);

  private final Customer customerMock = mock(Customer.class);

  private final String validId = "e3119506-030a-4877-a219-389ef21118a4";
  private CustomerIdDTO validInputDTO = new CustomerIdDTO(validId);

  void prepareDefaultStubs() throws BusinessException, DataAccessException {
    doReturn(customerMock).when(customerDataAccessMock).read(any());
  }

  @Test
  void shoudlCallDataAccessCorrectly() throws BusinessException, DomainException {
    // Deactivating
    prepareDefaultStubs();
    doReturn(true).when(customerMock).isActive();
    deactivateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(1)).save(any());

    // Deactivation not needed
    reset(customerDataAccessMock);
    prepareDefaultStubs();
    doReturn(false).when(customerMock).isActive();
    deactivateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(0)).save(any());
  }

  @Test
  void shouldDeactivateCustomer() throws BusinessException, DomainException {
    // Deactivating
    prepareDefaultStubs();
    doReturn(true).when(customerMock).isActive();
    deactivateCustomerUseCase.execute(validInputDTO);
    verify(customerMock, times(1)).deactivate();
    verify(customerDataAccessMock, times(1)).save(any());
  }

  @Nested
  class DeactivatingCustomerShouldThrow {

    @Test
    void whenIdIsNull() throws DomainException, BusinessException {
      prepareDefaultStubs();
      assertThrows(DomainException.class, () -> deactivateCustomerUseCase.execute(new CustomerIdDTO(null)));
      assertThrows(DomainException.class, () -> deactivateCustomerUseCase.execute(new CustomerIdDTO("")));
    }

    @Test
    void whenCustomerNotFound() throws DomainException, BusinessException {
      doReturn(null).when(customerDataAccessMock).read(any());
      assertThrows(DomainException.class, () -> deactivateCustomerUseCase.execute(validInputDTO));
    }

  }

}
