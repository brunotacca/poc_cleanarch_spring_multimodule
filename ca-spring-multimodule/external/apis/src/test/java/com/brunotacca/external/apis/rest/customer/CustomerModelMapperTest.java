package com.brunotacca.external.apis.rest.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerModelMapperTest {

  private final NewCustomerModel validNewCustomerInput = new NewCustomerModel("name", "email", "street", "number", "city", "zip");
  private final CustomerModel validCustomerModel = new CustomerModel("ID", "name", "email", false, "street", "number", "city", "zip");
  private final CustomerOutputDTO validCustomerOutputDTO = new CustomerOutputDTO("ID", "name", "email", false, "street", "number", "city", "zip");

  @Test
  void shouldCreateInputFromModel() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    CreateCustomerInputDTO output = mapper.inputFromModel(validNewCustomerInput);

    assertEquals(validNewCustomerInput.getName(), output.name());
    assertEquals(validNewCustomerInput.getEmail(), output.email());
    assertEquals(validNewCustomerInput.getStreet(), output.street());
    assertEquals(validNewCustomerInput.getNumber(), output.number());
    assertEquals(validNewCustomerInput.getCity(), output.city());
    assertEquals(validNewCustomerInput.getZip(), output.zip());
  }

  @Test
  void shouldCreateOutputFromModel() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    CustomerOutputDTO output = mapper.outputFromModel(validCustomerModel);

    assertEquals(validCustomerModel.getId(), output.id());
    assertEquals(validCustomerModel.getName(), output.name());
    assertEquals(validCustomerModel.getEmail(), output.email());
    assertEquals(validCustomerModel.getActive(), output.active());
    assertEquals(validCustomerModel.getStreet(), output.street());
    assertEquals(validCustomerModel.getNumber(), output.number());
    assertEquals(validCustomerModel.getCity(), output.city());
    assertEquals(validCustomerModel.getZip(), output.zip());
  }

  @Test
  void shouldCreateModelFromOutput() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    CustomerModel output = mapper.modelFromOutput(validCustomerOutputDTO);

    assertEquals(validCustomerOutputDTO.id(), output.getId());
    assertEquals(validCustomerOutputDTO.name(), output.getName());
    assertEquals(validCustomerOutputDTO.email(), output.getEmail());
    assertEquals(validCustomerOutputDTO.active(), output.getActive());
    assertEquals(validCustomerOutputDTO.street(), output.getStreet());
    assertEquals(validCustomerOutputDTO.number(), output.getNumber());
    assertEquals(validCustomerOutputDTO.city(), output.getCity());
    assertEquals(validCustomerOutputDTO.zip(), output.getZip());
  }


}
