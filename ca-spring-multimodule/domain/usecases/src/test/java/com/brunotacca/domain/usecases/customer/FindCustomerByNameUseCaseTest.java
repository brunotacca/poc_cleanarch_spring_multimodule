package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerNameInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class FindCustomerByNameUseCaseTest {

  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private CustomerMapper customerMapperMock = mock(CustomerMapper.class);
  private FindCustomerByNameUseCase findCustomerByNameUseCase = new FindCustomerByNameUseCase(customerDataAccessMock, customerMapperMock);

  private final Customer customerMock = mock(Customer.class);
  private final Customer customerMock2 = mock(Customer.class);
  private final List<Customer> listCustomersMock = List.of(customerMock, customerMock2);

  private final String validName = "Foo Bar";
  private CustomerNameInputDTO validInputDTO = new CustomerNameInputDTO(validName);

  void prepareDefaultStubs() throws BusinessException, DataAccessException {
    doReturn(listCustomersMock).when(customerDataAccessMock).findByName(any());
  }

  @Test
  void shoudlCallDataAccessCorrectly() throws BusinessException, DomainException {
    prepareDefaultStubs();
    findCustomerByNameUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).findByName(validName);
    verify(customerMapperMock, times(2)).outputFromEntity(any());
  }

  @Nested
  class FindCustomerByNameShouldThrow {

    @Test
    void whenNameIsInvalid() throws BusinessException {
      // Test throw for Address
      assertThrows(DomainException.class,() -> findCustomerByNameUseCase.execute(new CustomerNameInputDTO(null)));
      assertThrows(DomainException.class,() -> findCustomerByNameUseCase.execute(new CustomerNameInputDTO("")));
      assertThrows(DomainException.class,() -> findCustomerByNameUseCase.execute(new CustomerNameInputDTO("  ")));
      assertThrows(DomainException.class,() -> findCustomerByNameUseCase.execute(new CustomerNameInputDTO("12")));
    }

    @Test
    void whenDataAccessThrows() throws BusinessException, DataAccessException {
      prepareDefaultStubs();
      doThrow(new DataAccessException("Test cause")).when(customerDataAccessMock).findByName(any());
      assertThrows(DataAccessException.class,() -> findCustomerByNameUseCase.execute(validInputDTO));
    }

  }

}
