package com.brunotacca.external.apis.rest.customer;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

  private final CustomerController customerController;
  private final CustomerModelMapper customerModelMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HttpHeaders createCustomer(@RequestBody @Valid NewCustomerModel customer) throws ResponseStatusException {
    CustomerOutputDTO output = null;
    
    try {
      output = customerController.createCustomer(customerModelMapper.inputFromModel(customer));
    } catch (DomainException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(linkTo(CustomerRestController.class).slash(output.id()).toUri());

    return httpHeaders;
  }

}
