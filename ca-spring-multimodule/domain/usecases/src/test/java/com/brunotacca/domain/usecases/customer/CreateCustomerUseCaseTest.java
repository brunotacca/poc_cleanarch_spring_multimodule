package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;
import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerMapper;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.BusinessValidationException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CreateCustomerUseCaseTest {

  private final CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private final CustomerFactory customerFactoryMock = mock(CustomerFactory.class);
  private final CustomerMapper customerMapperMock = mock(CustomerMapper.class);
  private final CreateCustomerUseCase createCustomerUseCase = new CreateCustomerUseCase(customerDataAccessMock,customerFactoryMock,customerMapperMock);

  private final Customer customerMock = mock(Customer.class, Mockito.RETURNS_DEEP_STUBS);
  private final Address addressMock = mock(Address.class);

  private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
  private final String validName = "Foo Bar";
  private final String validEmail = "foo@bar.com";
  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";
  private CreateCustomerInputDTO validInputDTO = new CreateCustomerInputDTO(validName, validEmail, validStreet, validNumber, validCity, validZip);
  private CustomerOutputDTO validOutputDTO = new CustomerOutputDTO(validId, validName, validEmail, false, validStreet, validNumber, validCity, validZip);

  void prepareStubs() throws BusinessException {
    when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
    doReturn(customerMock).when(customerFactoryMock).createCustomer(anyString(), anyString(), any());
    when(customerMapperMock.outputFromEntity(any())).thenReturn(validOutputDTO);
  }

  @Test
  void shoudlCallFactoriesAndDataAccess() throws BusinessException, DomainException {
    prepareStubs();
    createCustomerUseCase.execute(validInputDTO);
    verify(customerFactoryMock, times(1)).createCustomer(validInputDTO.name(), validInputDTO.email(), addressMock);
    verify(customerFactoryMock, times(1)).createAddress(validInputDTO.street(), validInputDTO.number(), validInputDTO.zip(), validInputDTO.city());
    verify(customerDataAccessMock, times(1)).create(any());
  }

  @Test
  void shouldReturnCustomer() throws DomainException, BusinessException {
    prepareStubs();
    CustomerOutputDTO outputDTO = createCustomerUseCase.execute(validInputDTO);
    assertNotNull(outputDTO);
  }

  @Nested
  class CreatingCustomerShouldThrow {

    @Test
    void whenEntitiesThrows() throws BusinessException {
      // Test throw for Address
      when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenThrow(new BusinessException(""));
      assertThrows(BusinessValidationException.class,() -> createCustomerUseCase.execute(validInputDTO));
      
      // Test throw for Customer
      reset(customerFactoryMock);
      when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
      when(customerFactoryMock.createCustomer(anyString(), anyString(), any())).thenThrow(new BusinessException(""));
      assertThrows(BusinessValidationException.class,() -> createCustomerUseCase.execute(validInputDTO));
    }

    @Test
    void withoutUniqueEmail() throws BusinessException, DataAccessException {
      prepareStubs();
      doReturn(Optional.of(customerMock)).when(customerDataAccessMock).findByEmail(any());

      DomainException thrown = assertThrows(DomainException.class,() -> createCustomerUseCase.execute(validInputDTO));
      assertTrue(thrown.getMessage().contains("There is already a customer with this email."));
    }

    @Test
    void whenDataAccessThrows() throws BusinessException, DataAccessException {
      prepareStubs();
      doThrow(new DataAccessException("Test cause")).when(customerDataAccessMock).create(any());
      assertThrows(DataAccessException.class,() -> createCustomerUseCase.execute(validInputDTO));
    }

  }

}
