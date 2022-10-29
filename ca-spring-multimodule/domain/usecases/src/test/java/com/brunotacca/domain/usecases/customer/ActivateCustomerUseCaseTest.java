package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFixtures;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class ActivateCustomerUseCaseTest {
  
  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private ActivateCustomerUseCase activateCustomerUseCase = new ActivateCustomerUseCase(customerDataAccessMock);

  private final Customer customerMock = mock(Customer.class);

  private final UUID validId = CustomerFixtures.VALID_ID;
  private CustomerIdDTO validInputDTO = new CustomerIdDTO(validId);

  @BeforeEach
  void beforeEach() throws BusinessException, DomainException {
    doReturn(Optional.of(customerMock)).when(customerDataAccessMock).read(any());
  }

  @Test
  void shoudlCallDataAccessCorrectlyWhenSaveNeeded() throws BusinessException, DomainException {
    // Activating
    doReturn(false).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(1)).update(any());
  }

  @Test
  void shoudlCallDataAccessCorrectlyWhenSaveNotNeeded() throws BusinessException, DomainException {
    // Activation not needed
    doReturn(true).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerDataAccessMock, times(0)).update(any());
  }

  @Test
  void shouldActivateCustomer() throws BusinessException, DomainException {
    // Activating
    doReturn(false).when(customerMock).isActive();
    activateCustomerUseCase.execute(validInputDTO);
    verify(customerMock, times(1)).activate();
    verify(customerDataAccessMock, times(1)).update(any());
  }

  @Nested
  class ActivatingCustomerShouldThrow {

    @Test
    void whenIdIsNull() throws DomainException, BusinessException {
      assertThrows(DomainException.class, () -> activateCustomerUseCase.execute(new CustomerIdDTO(null)));
    }

    @Test
    void whenCustomerNotFound() throws DomainException, BusinessException {
      doReturn(Optional.empty()).when(customerDataAccessMock).read(any());
      assertThrows(DomainException.class, () -> activateCustomerUseCase.execute(validInputDTO));
    }

  }

}
