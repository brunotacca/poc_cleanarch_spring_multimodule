package com.brunotacca.external.apis.rest.customers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

  Links linksForCustomerCreation(String newCustomerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).getById(newCustomerId)).toUri().toASCIIString();
    Link link = Link.of(uri, IanaLinkRelations.EDIT_VALUE);
    return Links.of(link);
  }

  Links linksForGetCustomer(String customerId) {
    Link selfLinkWithAffordances = this.linkSelfCustomerId(customerId).withSelfRel()
      .andAffordance(afford(methodOn(CustomersRestController.class).update(null, customerId)))
      .andAffordance(afford(methodOn(CustomersRestController.class).activate(customerId)))
      .andAffordance(afford(methodOn(CustomersRestController.class).deactivate(customerId)));

    return Links.of(selfLinkWithAffordances);
  }

  Links linksForFindCustomerByName(String customerName) {
    String selfUri = linkTo(methodOn(CustomersRestController.class).findCustomerByName(customerName)).toUri().toASCIIString();
    Link selfLink = Link.of(selfUri).withSelfRel();

    return Links.of(selfLink);
  }

  Links linksForActivateCustomer(String customerId) {
    Link getByIdLink = this.linkSelfCustomerId(customerId);
    Link deactivateLink = this.linkDeactivate(customerId);

    return Links.of(getByIdLink, deactivateLink);    
  }

  Links linksForDeactivateCustomer(String customerId) {
    Link getByIdLink = this.linkSelfCustomerId(customerId);
    Link activateLink = this.linkActivate(customerId);

    return Links.of(getByIdLink, activateLink);
  }

  private Link linkSelfCustomerId(String customerId) {
    String selfUri = linkTo(methodOn(CustomersRestController.class).getById(customerId)).toUri().toASCIIString();
    return Link.of(selfUri).withSelfRel();
  }

  private Link linkActivate(String customerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).activate(customerId)).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.ACTIVATE);
  }

  private Link linkDeactivate(String customerId) {
    String uri = linkTo(methodOn(CustomersRestController.class).deactivate(customerId)).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.DEACTIVATE);
  }

}