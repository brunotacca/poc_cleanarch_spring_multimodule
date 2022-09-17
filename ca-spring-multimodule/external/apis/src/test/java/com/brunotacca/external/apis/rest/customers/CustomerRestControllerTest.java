package com.brunotacca.external.apis.rest.customers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerRestControllerTest {

  private final UUID validId = UUID.fromString("55951aeb-4fc8-4ba4-b78a-020138b13d22");
  private final String validIdString = validId.toString();
  private final String validName = "Foo Bar";

  private final ExistingCustomerModel existingCustomerModel = new ExistingCustomerModel(validId, validName, "email", false, "street", "number", "city", "zip");
  private final CustomerModel validNewCustomerInput = new CustomerModel(validName, "email", "street", "number", "city", "zip");
  private final CustomerModel invalidNewCustomerInput = new CustomerModel("", "", "", "", "", "");
  private final UpdateCustomerInputDTO validUpdateCustomerInputDTO = new UpdateCustomerInputDTO(validId, validName, "email", "street", "number", "city", "zip");

  private final CustomerController customerController = mock(CustomerController.class);
  private final CustomerModelMapper customerModelMapper = mock(CustomerModelMapper.class);
  
  private final CustomerLinkDiscoverabilityFactory customerLinkDiscoverabilityFactory = mock(CustomerLinkDiscoverabilityFactory.class, RETURNS_DEEP_STUBS);

  private CustomersRestController customerRestController;
  private CustomerOutputDTO outputDTO = new CustomerOutputDTO(validId, validName, "email", false, "street", "number", "city", "zip");

  @BeforeEach
  void beforeEach() {
    this.customerRestController = new CustomersRestController(this.customerController, this.customerModelMapper, this.customerLinkDiscoverabilityFactory);
  }


  @Nested
  class Create {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      when(customerController.createCustomer(any())).thenReturn(outputDTO);
  
      customerRestController.create(validNewCustomerInput);
      verify(customerModelMapper, times(1)).createDtoFromModel(validNewCustomerInput);
      verify(customerLinkDiscoverabilityFactory, times(1)).linksForCustomerCreation(validId);
      verify(customerController, times(1)).createCustomer(any());
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      when(customerController.createCustomer(any())).thenThrow(new DomainException("message"));
      assertThrows(ResponseStatusException.class,() -> customerRestController.create(invalidNewCustomerInput));
    }

  }

  @Nested
  class GetById {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      when(customerController.getCustomer(any())).thenReturn(outputDTO);
      when(customerModelMapper.modelFromOutput(any())).thenReturn(existingCustomerModel);
  
      customerRestController.getById(validIdString);
      verify(customerController, times(1)).getCustomer(any());
      verify(customerLinkDiscoverabilityFactory, times(1)).linksForGetCustomer(validId);
      verify(customerModelMapper, times(1)).modelFromOutput(outputDTO);
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      when(customerController.getCustomer(any())).thenThrow(new DomainException("message"));
      assertThrows(ResponseStatusException.class,() -> customerRestController.getById(validIdString));
    }
  }

  @Nested
  class FindCustomerByName {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      when(customerController.findCustomer(any())).thenReturn(List.of(outputDTO, outputDTO));
      when(customerModelMapper.modelFromOutput(any())).thenReturn(existingCustomerModel);
  
      customerRestController.findCustomerByName(validName);
      verify(customerLinkDiscoverabilityFactory, times(2)).linksForGetCustomer(validId);
      verify(customerModelMapper, times(2)).modelFromOutput(outputDTO);
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      when(customerController.findCustomer(any())).thenThrow(new DomainException("message"));
      assertThrows(ResponseStatusException.class,() -> customerRestController.findCustomerByName(validName));
    }
  }
  
  @Nested
  class Update {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      when(customerController.updateCustomer(any())).thenReturn(outputDTO);
      when(customerModelMapper.updateDtoFromModel(any(), any())).thenReturn(validUpdateCustomerInputDTO);
      when(customerModelMapper.modelFromOutput(any())).thenReturn(existingCustomerModel);

      customerRestController.update(validNewCustomerInput, validIdString);
      verify(customerModelMapper, times(1)).updateDtoFromModel(validNewCustomerInput, validId);
      verify(customerController, times(1)).updateCustomer(any());
      verify(customerLinkDiscoverabilityFactory, times(1)).linksForGetCustomer(validId);
      verify(customerModelMapper, times(1)).modelFromOutput(outputDTO);
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      when(customerController.updateCustomer(any())).thenThrow(new DomainException("message"));
      assertThrows(ResponseStatusException.class,() -> customerRestController.update(validNewCustomerInput, validIdString));
    }
  }
  
  @Nested
  class Activate {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      customerRestController.activate(validIdString);
      verify(customerLinkDiscoverabilityFactory, times(1)).linksForActivateCustomer(validId);
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      doThrow(new DomainException("message")).when(customerController).activateCustomer(any());
      assertThrows(ResponseStatusException.class,() -> customerRestController.activate(validName));
    }
  }
  
  @Nested
  class Deactivate {
    @Test
    void shouldCallControllerMapperAndLinks() throws DomainException {
      customerRestController.deactivate(validIdString);
      verify(customerLinkDiscoverabilityFactory, times(1)).linksForDeactivateCustomer(validId);
    }

    @Test
    void shouldCatchDomainExceptionsAndThrowResponseStatus() throws DomainException {
      doThrow(new DomainException("message")).when(customerController).deactivateCustomer(any());
      assertThrows(ResponseStatusException.class,() -> customerRestController.deactivate(validName));
    }
  }
  
}
