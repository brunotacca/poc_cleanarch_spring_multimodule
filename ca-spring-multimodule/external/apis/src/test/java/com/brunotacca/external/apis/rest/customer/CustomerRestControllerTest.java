package com.brunotacca.external.apis.rest.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerRestControllerTest {

  private final NewCustomerModel validNewCustomerInput = new NewCustomerModel("name", "email", "street", "number", "city", "zip");
  private final NewCustomerModel invalidNewCustomerInput = new NewCustomerModel("", "", "", "", "", "");

  private final CustomerController customerController = mock(CustomerController.class);
  private final CustomerModelMapper customerModelMapper = mock(CustomerModelMapper.class);
  private CustomerRestController customerRestController;
  private CustomerOutputDTO outputDTO = new CustomerOutputDTO("id", "name", "email", false, "street", "number", "city", "zip");

  @BeforeEach
  void beforeEach() {
    this.customerRestController = new CustomerRestController(this.customerController, this.customerModelMapper);
  }

  @Test
  void shouldCallControllerAndMapper() throws DomainException {
    when(customerController.createCustomer(any())).thenReturn(outputDTO);

    this.customerRestController.createCustomer(validNewCustomerInput);
    verify(customerModelMapper, times(1)).inputFromModel(validNewCustomerInput);
    verify(customerController, times(1)).createCustomer(any());
  }
  
  @Test
  void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {

    when(customerController.createCustomer(any())).thenThrow(new DomainException("message"));
    assertThrows(ResponseStatusException.class,() -> this.customerRestController.createCustomer(invalidNewCustomerInput));

  }
  

}
