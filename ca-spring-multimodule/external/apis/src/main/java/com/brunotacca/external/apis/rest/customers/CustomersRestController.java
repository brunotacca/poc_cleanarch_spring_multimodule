package com.brunotacca.external.apis.rest.customers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.dto.CustomerIdDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerNameInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import com.brunotacca.external.apis.rest.hateoas.ApiLinkRelations;
import com.brunotacca.external.apis.rest.hateoas.EmptyBodyModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/"+ApiLinkRelations.CUSTOMERS)
class CustomersRestController {

  private final CustomerController customerController;
  private final CustomerModelMapper customerModelMapper;
  private final CustomerLinkDiscoverabilityFactory customerLinkDiscoverabilityFactory;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<EmptyBodyModel> create(@RequestBody @Valid CustomerModel customer) throws ResponseStatusException {
    CustomerOutputDTO output = null;
    
    try {
      output = customerController.createCustomer(customerModelMapper.createDtoFromModel(customer));
    } catch (DomainException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    Links links = customerLinkDiscoverabilityFactory.linksForCustomerCreation(output.id());

    return ResponseEntity
            .created(URI.create("/"+output.id()))
            .body(new EmptyBodyModel().add(links));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExistingCustomerModel> getById(@PathVariable @NotBlank String id) throws ResponseStatusException {
    CustomerOutputDTO output = null;

    try {
      UUID uuid = UUID.fromString(id);
      output = customerController.getCustomer(new CustomerIdDTO(uuid));
    } catch (DomainException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    Links links = customerLinkDiscoverabilityFactory.linksForGetCustomer(output.id());
    return ResponseEntity.ok(customerModelMapper.modelFromOutput(output).add(links));
  }

  @GetMapping
  public ResponseEntity<CollectionModel<ExistingCustomerModel>> findCustomerByName(@RequestParam("name") @NotBlank String name) throws ResponseStatusException {
    List<CustomerOutputDTO> output = null;

    try {
      output = customerController.findCustomer(new CustomerNameInputDTO(name));
    } catch (DomainException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    List<ExistingCustomerModel> result = output.stream()
        .map(temp -> {
          ExistingCustomerModel model = customerModelMapper.modelFromOutput(temp);
          model.add(customerLinkDiscoverabilityFactory.linksForGetCustomer(model.getId()));
          return model;
        })
        .toList();

    CollectionModel<ExistingCustomerModel> collectionResult = CollectionModel.of(result);
    collectionResult.add(customerLinkDiscoverabilityFactory.linksForFindCustomerByName(name));
    
    return ResponseEntity.ok(collectionResult);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExistingCustomerModel> update(@RequestBody @Valid CustomerModel customer, @PathVariable @NotBlank String id) throws ResponseStatusException {
    CustomerOutputDTO output = null;

    try {
      UUID uuid = UUID.fromString(id);
      output = customerController.updateCustomer(customerModelMapper.updateDtoFromModel(customer, uuid));
    } catch (DomainException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    Links links = customerLinkDiscoverabilityFactory.linksForGetCustomer(output.id());
    return ResponseEntity.ok(customerModelMapper.modelFromOutput(output).add(links));
  }

  @PatchMapping("/{id}/activate")
  public ResponseEntity<EmptyBodyModel> activate(@PathVariable @NotBlank String id) throws ResponseStatusException {
    
    UUID uuid = null;
    try {
      uuid = UUID.fromString(id);
      customerController.activateCustomer(new CustomerIdDTO(uuid));
    } catch (DomainException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    Links links = customerLinkDiscoverabilityFactory.linksForActivateCustomer(uuid);

    return ResponseEntity
            .accepted()
            .body(new EmptyBodyModel().add(links));
  }

  @PatchMapping("/{id}/deactivate")
  public ResponseEntity<EmptyBodyModel> deactivate(@PathVariable @NotBlank String id) throws ResponseStatusException {
    
    UUID uuid = null;
    try {
      uuid = UUID.fromString(id);
      customerController.deactivateCustomer(new CustomerIdDTO(uuid));
    } catch (DomainException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    Links links = customerLinkDiscoverabilityFactory.linksForDeactivateCustomer(uuid);

    return ResponseEntity
            .accepted()
            .body(new EmptyBodyModel().add(links));
  }

}
