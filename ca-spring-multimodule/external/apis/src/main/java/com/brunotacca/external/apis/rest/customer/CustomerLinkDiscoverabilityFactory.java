package com.brunotacca.external.apis.rest.customer;

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
    String baseId = linkTo(methodOn(CustomerRestController.class).getById("id")).toUri().toASCIIString();
    Link base = Link.of(baseId, ApiLinkRelations.CUSTOMER);
    Link search = linkTo(methodOn(CustomerRestController.class).findCustomerByName("customerName")).withRel(ApiLinkRelations.CUSTOMER+":"+IanaLinkRelations.SEARCH_VALUE);

    return Links.of(base, search);
  }

  Links linksForCustomerCreation(String newCustomerId) {
    String uri = linkTo(methodOn(CustomerRestController.class).getById(newCustomerId)).toUri().toASCIIString();
    Link link = Link.of(uri, IanaLinkRelations.EDIT_VALUE);
    return Links.of(link);
  }

  Links linksForGetCustomer(String customerId) {
    String selfUri = linkTo(methodOn(CustomerRestController.class).getById(customerId)).toUri().toASCIIString();
    Link selfLinkWithAffordances = Link.of(selfUri).withSelfRel()
      .andAffordance(afford(methodOn(CustomerRestController.class).update(null, customerId)))
      .andAffordance(afford(methodOn(CustomerRestController.class).activate(customerId)))
      .andAffordance(afford(methodOn(CustomerRestController.class).deactivate(customerId)));

    return Links.of(selfLinkWithAffordances);
  }

  Links linksForActivateCustomer(String customerId) {
    Link getByIdLink = this.linkGetById(customerId);
    Link deactivateLink = this.linkDeactivate(customerId);

    return Links.of(getByIdLink, deactivateLink);    
  }

  Links linksForDeactivateCustomer(String customerId) {
    Link getByIdLink = this.linkGetById(customerId);
    Link activateLink = this.linkActivate(customerId);

    return Links.of(getByIdLink, activateLink);
  }

  private Link linkGetById(String customerId) {
    String uri = linkTo(methodOn(CustomerRestController.class).getById(customerId)).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.CUSTOMER);
  }

  private Link linkActivate(String customerId) {
    String uri = linkTo(methodOn(CustomerRestController.class).activate(customerId)).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.ACTIVATE);
  }

  private Link linkDeactivate(String customerId) {
    String uri = linkTo(methodOn(CustomerRestController.class).deactivate(customerId)).toUri().toASCIIString();
    return Link.of(uri, ApiLinkRelations.DEACTIVATE);
  }

}