package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class GetCustomerByIdUseCaseTest {

  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private CustomerMapper customerMapperMock = mock(CustomerMapper.class);
  private GetCustomerByIdUseCase getCustomerByIdUseCase = new GetCustomerByIdUseCase(customerDataAccessMock, customerMapperMock);

  private final Customer customerMock = mock(Customer.class);

  private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
  private CustomerIdDTO validInputDTO = new CustomerIdDTO(validId);

  @BeforeEach
  void beforeEach() throws DataAccessException {
    doReturn(Optional.of(customerMock)).when(customerDataAccessMock).read(any());
  }

  @Test
  void shoudlCallDataAccessCorrectly() throws BusinessException, DomainException {
    getCustomerByIdUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).read(validId);
    verify(customerMapperMock, times(1)).outputFromEntity(any());
  }

  @Nested
  class GetCustomerByIdShouldThrow {

    @Test
    void whenIdIsInvalid() throws BusinessException {
      assertThrows(DomainException.class,() -> getCustomerByIdUseCase.execute(new CustomerIdDTO(null)));
    }

    @Test
    void whenDataAccessThrows() throws BusinessException, DataAccessException {
      doThrow(new DataAccessException("Test cause")).when(customerDataAccessMock).read(any());
      assertThrows(DataAccessException.class,() -> getCustomerByIdUseCase.execute(validInputDTO));
    }

  }

}
