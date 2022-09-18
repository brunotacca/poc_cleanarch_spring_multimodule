package com.brunotacca.external.apis.rest.customers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Component;

import com.brunotacca.external.apis.rest.hateoas.ApiLinkRelations;

@Component
public class CustomerLinkDiscoverabilityFactory {

  public Links indexRoot() {
    String baseId = linkTo(methodOn(CustomersRestController.class).getById("id")).toUri().toASCIIString();
    Link base = Link.of(baseId, ApiLinkRelations.CUSTOMERS);
    Link search = linkTo(methodOn(CustomersRestController.class).findCustomerByName("customerName")).withRel(ApiLinkRelations.CUSTOMERS+":"+IanaLinkRelations.SEARCH_VALUE);

    return Links.of(base, search);
  }

  Links linksForCustomerCreation(UUID newCustomerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).getById(newCustomerId.toString())).toUri().toASCIIString();
    Link link = Link.of(uri, IanaLinkRelations.EDIT_VALUE);
    return Links.of(link);
  }

  Links linksForGetCustomer(UUID customerId) {
    Link selfLinkWithAffordances = this.linkSelfCustomerId(customerId).withSelfRel()
      .andAffordance(afford(methodOn(CustomersRestController.class).update(null, customerId.toString())))
      .andAffordance(afford(methodOn(CustomersRestController.class).activate(customerId.toString())))
      .andAffordance(afford(methodOn(CustomersRestController.class).deactivate(customerId.toString())));

    return Links.of(selfLinkWithAffordances);
  }

  Links linksForFindCustomerByName(String customerName) {
    String selfUri = linkTo(methodOn(CustomersRestController.class).findCustomerByName(customerName)).toUri().toASCIIString();
    Link selfLink = Link.of(selfUri).withSelfRel();

    return Links.of(selfLink);
  }

  Links linksForActivateCustomer(UUID customerId) {
    Link getByIdLink = this.linkSelfCustomerId(customerId);
    Link deactivateLink = this.linkDeactivate(customerId);

    return Links.of(getByIdLink, deactivateLink);    
  }

  Links linksForDeactivateCustomer(UUID customerId) {
    Link getByIdLink = this.linkSelfCustomerId(customerId);
    Link activateLink = this.linkActivate(customerId);

    return Links.of(getByIdLink, activateLink);
  }

  private Link linkSelfCustomerId(UUID customerId) {
    String selfUri = linkTo(methodOn(CustomersRestController.class).getById(customerId.toString())).toUri().toASCIIString();
    return Link.of(selfUri).withSelfRel();
  }

  private Link linkActivate(UUID customerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).activate(customerId.toString())).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.ACTIVATE);
  }

  private Link linkDeactivate(UUID customerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).deactivate(customerId.toString())).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.DEACTIVATE);
  }

}