package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CreateCustomerUseCaseTest {

  private final CustomerDataAccess customerDataAccessMock = mock(CustomerDataAccess.class);
  private final CustomerFactory customerFactoryMock = mock(CustomerFactory.class);
  private final CustomerMapper customerMapperMock = mock(CustomerMapper.class);
  private final CreateCustomerUseCase createCustomerUseCase = new CreateCustomerUseCase(customerDataAccessMock,customerFactoryMock,customerMapperMock);

  private final Customer customerMock = mock(Customer.class, Mockito.RETURNS_DEEP_STUBS);
  private final Address addressMock = mock(Address.class);


  private final String validName = "Foo Bar";
  private final String validEmail = "foo@bar.com";
  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";
  private CreateCustomerInputDTO validInputDTO = new CreateCustomerInputDTO(validName, validEmail, validStreet, validNumber, validCity, validZip);
  private CustomerOutputDTO validOutputDTO = new CustomerOutputDTO("id", validName, validEmail, false, validStreet, validNumber, validCity, validZip);

  void prepareStubsValidData() throws BusinessException {
    when(customerFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
    when(customerFactoryMock.createCustomer(anyString(), anyString(), any())).thenReturn(customerMock);
    when(customerMapperMock.fromCustomer(any())).thenReturn(validOutputDTO);
  }

  @Test
  void shoudlCallFactoriesAndDataAccess() throws BusinessException, DomainException {
    prepareStubsValidData();
    createCustomerUseCase.execute(validInputDTO);
    verify(customerFactoryMock, times(1)).createCustomer(anyString(), anyString(), any());
    verify(customerFactoryMock, times(1)).createAddress(anyString(), anyString(), anyString(), anyString());
    verify(customerDataAccessMock, times(1)).save(any());
  }

  @Test
  void shouldReturnCustomer() throws DomainException, BusinessException {
    prepareStubsValidData();
    CustomerOutputDTO outputDTO = createCustomerUseCase.execute(validInputDTO);
    assertNotNull(outputDTO);
  }

  // @Test
  // void shouldReturnCustomerWithCorrectData() {
  //   // assertNotNull(outputDTO);
  //   // assertNotNull(outputDTO.id());
  //   // assertNotSame("", outputDTO.id().trim());
  //   // assertEquals(validName, outputDTO.name());
  //   // assertEquals(validEmail, outputDTO.email());
  //   // assertEquals(validStreet, outputDTO.street());
  //   // assertEquals(validNumber, outputDTO.number());
  //   // assertEquals(validCity, outputDTO.city());
  //   // assertEquals(validZip, outputDTO.zip());
  // }

  @Nested
  class CreatingCustomerShouldThrow {

    @Test
    void withInvalidParameters() {

    }

    @Test
    void withoutUniqueEmail() {

    }

  }

}
