package com.brunotacca.external.apis.rest.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.customer.dto.UpdateCustomerInputDTO;
import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerModelMapperTest {

  private final UUID validId = UUID.fromString("55951aeb-4fc8-4ba4-b78a-020138b13d22");
  private final CustomerModel validNewCustomerInput = new CustomerModel("name", "email", "street", "number", "city", "zip");
  private final ExistingCustomerModel validCustomerModel = new ExistingCustomerModel(validId, "name", "email", false, "street", "number", "city", "zip");
  private final CustomerOutputDTO validCustomerOutputDTO = new CustomerOutputDTO(validId, "name", "email", false, "street", "number", "city", "zip");

  @Test
  void shouldCreateInputFromModel() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    CreateCustomerInputDTO createDto = mapper.createDtoFromModel(validNewCustomerInput);

    assertEquals(validNewCustomerInput.getName(), createDto.name());
    assertEquals(validNewCustomerInput.getEmail(), createDto.email());
    assertEquals(validNewCustomerInput.getStreet(), createDto.street());
    assertEquals(validNewCustomerInput.getNumber(), createDto.number());
    assertEquals(validNewCustomerInput.getCity(), createDto.city());
    assertEquals(validNewCustomerInput.getZip(), createDto.zip());
  }

  @Test
  void shouldCreateUpdateDtoFromModel() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    UpdateCustomerInputDTO updateDto = mapper.updateDtoFromModel(validNewCustomerInput, validId);

    assertEquals(validId, updateDto.id());
    assertEquals(validNewCustomerInput.getName(), updateDto.name());
    assertEquals(validNewCustomerInput.getEmail(), updateDto.email());
    assertEquals(validNewCustomerInput.getStreet(), updateDto.street());
    assertEquals(validNewCustomerInput.getNumber(), updateDto.number());
    assertEquals(validNewCustomerInput.getCity(), updateDto.city());
    assertEquals(validNewCustomerInput.getZip(), updateDto.zip());
  }


  @Test
  void shouldCreateOutputFromModel() {
    CustomerModelMapper mapper = new CustomerModelMapper();

    CustomerOutputDTO output = mapper.outputDtoFromModel(validCustomerModel);

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

    ExistingCustomerModel output = mapper.modelFromOutput(validCustomerOutputDTO);

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
