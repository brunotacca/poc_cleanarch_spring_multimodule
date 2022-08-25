package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.BusinessValidationException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class UpdateCustomerUseCaseTest {
  
  private CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private CustomerFactory customerFactoryMock = mock(CustomerFactory.class);
  private CustomerMapper customerMapperMock = mock(CustomerMapper.class);
  private UpdateCustomerUseCase updateCustomerUseCase = new UpdateCustomerUseCase(customerDataAccessMock,customerFactoryMock,customerMapperMock);

  private final Customer customerMock = mock(Customer.class);
  private final Customer customerMock2 = mock(Customer.class);
  private final Customer customerMock3 = mock(Customer.class);
  private final Address addressMock = mock(Address.class);

  private final String validId = "e3119506-030a-4877-a219-389ef21118a4";
  private final String validName = "Foo Bar";
  private final String validEmail = "foo@bar.com";
  private final String validEmail2 = "bar@bar.com";
  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";
  private UpdateCustomerInputDTO validInputDTO = new UpdateCustomerInputDTO(validId, validName, validEmail, validStreet, validNumber, validCity, validZip);
  private CustomerOutputDTO validOutputDTO = new CustomerOutputDTO(validId, validName, validEmail, false, validStreet, validNumber, validCity, validZip);

  void prepareDefaultStubs() throws BusinessException, DataAccessException {
    doReturn(addressMock).when(customerFactoryMock).createAddress(any(), any(), any(), any());
    
    doReturn(customerMock).when(customerFactoryMock).getExistingCustomer(any(), any(), any(), any());
    
    doReturn(customerMock2).when(customerDataAccessMock).read(any());
    doReturn(customerMock3).when(customerDataAccessMock).findByEmail(any());

    doReturn(validEmail).when(customerMock).getEmail();
    doReturn(validEmail).when(customerMock2).getEmail();
    doReturn(validEmail).when(customerMock3).getEmail();

    doReturn(validOutputDTO).when(customerMapperMock).outputFromEntity(any());
  }

  @Test
  void shoudlCallFactoriesAndDataAccessCorrectly() throws BusinessException, DomainException {
    // Same email path
    prepareDefaultStubs();
    updateCustomerUseCase.execute(validInputDTO);
    verify(customerFactoryMock, times(1)).createAddress(validInputDTO.street(), validInputDTO.number(), validInputDTO.zip(), validInputDTO.city());
    verify(customerFactoryMock, times(1)).getExistingCustomer(validInputDTO.id(), validInputDTO.name(), validInputDTO.email(), addressMock);
    verify(customerDataAccessMock, times(1)).read(any());
    verify(customerDataAccessMock, times(1)).save(any());

    // Different email path (email unique)
    reset(customerDataAccessMock);
    prepareDefaultStubs();
    doReturn(validEmail2).when(customerMock2).getEmail();
    doReturn(null).when(customerDataAccessMock).findByEmail(any());
    updateCustomerUseCase.execute(validInputDTO);
    verify(customerDataAccessMock, times(1)).findByEmail(any());

    // Different email path (email not unique)
    reset(customerDataAccessMock);
    prepareDefaultStubs();
    doReturn(validEmail2).when(customerMock2).getEmail();
    doReturn(customerMock3).when(customerDataAccessMock).findByEmail(any());
    assertThrows(DomainException.class, () -> updateCustomerUseCase.execute(validInputDTO));
    verify(customerDataAccessMock, times(1)).findByEmail(any());
  }

  @Test
  void shouldReturnCustomer() throws DomainException, BusinessException {
    prepareDefaultStubs();
    CustomerOutputDTO outputDTO = updateCustomerUseCase.execute(validInputDTO);
    assertNotNull(outputDTO);
  }

  @Nested
  class UpdatingCustomerShouldThrow {

    @Test
    void whenEntitiesThrows() throws BusinessException {
      // Test throw for Address
      when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenThrow(new BusinessException(""));
      assertThrows(BusinessValidationException.class,() -> updateCustomerUseCase.execute(validInputDTO));
      
      // Test throw for Customer
      reset(customerFactoryMock);
      when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
      when(customerFactoryMock.getExistingCustomer(anyString(), anyString(), anyString(), any())).thenThrow(new BusinessException(""));
      assertThrows(BusinessValidationException.class,() -> updateCustomerUseCase.execute(validInputDTO));
    }

    @Test
    void whenDataAccessThrows() throws BusinessException, DataAccessException {
      prepareDefaultStubs();
      doThrow(new DataAccessException("Test cause")).when(customerDataAccessMock).save(any());
      assertThrows(DataAccessException.class,() -> updateCustomerUseCase.execute(validInputDTO));
    }

  }


}
