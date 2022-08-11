package com.brunotacca.external.apis.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.dto.CreateCustomerInputDTO;
import com.brunotacca.domain.usecases.customer.dto.CustomerOutputDTO;
import com.brunotacca.domain.usecases.shared.exceptions.DomainException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestRestController {

  private final CustomerController customerController;

  @GetMapping
  public void getTest() throws DomainException {
    System.err.println("TestController.getTest()");

    CustomerOutputDTO output = customerController.createCustomer(new CreateCustomerInputDTO("name", "email", "street", "number", "city", "zip"));

    System.err.println("Customer: "+output);
  }

}