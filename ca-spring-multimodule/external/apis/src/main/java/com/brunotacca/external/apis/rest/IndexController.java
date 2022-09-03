package com.brunotacca.external.apis.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunotacca.external.apis.rest.customer.CustomerLinkDiscoverabilityFactory;
import com.brunotacca.external.apis.rest.hateoas.EmptyBodyModel;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Links;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

  private final CustomerLinkDiscoverabilityFactory customerLinkDiscoverabilityFactory;

  @GetMapping
  public EmptyBodyModel index() {
    Links linksForCustomer = customerLinkDiscoverabilityFactory.indexRoot();
    // order, etc...
    
    Links linksForIndex = Links.of(linksForCustomer.toList());
      // .merge ORDER
      // etc...

    return new EmptyBodyModel().add(linksForIndex);
  }

}
