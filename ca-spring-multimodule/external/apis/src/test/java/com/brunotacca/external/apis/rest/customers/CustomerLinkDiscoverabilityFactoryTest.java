package com.brunotacca.external.apis.rest.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.Links;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import com.brunotacca.external.apis.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerLinkDiscoverabilityFactoryTest {

  CustomerLinkDiscoverabilityFactory customerLinkDiscoverabilityFactory;
  private final String validId = "55951aeb-4fc8-4ba4-b78a-020138b13d22";

  @BeforeEach
  void beforeEach() {
    this.customerLinkDiscoverabilityFactory = new CustomerLinkDiscoverabilityFactory();
  }

  @Test
  void shouldGenerateLinksForIndexRoot() {

    Links links = this.customerLinkDiscoverabilityFactory.indexRoot();

    assertEquals(2, links.toList().size());

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("customers", "customers:search"));

  }
  
  @Test
  void shouldGenerateLinksForCustomerCreation() {

    Links links = this.customerLinkDiscoverabilityFactory.linksForCustomerCreation(validId);

    assertEquals(1, links.toList().size());

    List<String> linkHref = links.stream().map(l -> l.getHref()).toList();
    assertThat(linkHref, containsInAnyOrder(containsString(validId)));

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("edit"));

  }
  
  @Test
  void shouldGenerateLinksForGetCustomer() {

    Links links = this.customerLinkDiscoverabilityFactory.linksForGetCustomer(validId);

    assertEquals(1, links.toList().size());

    List<String> linkHref = links.stream().map(l -> l.getHref()).toList();
    assertThat(linkHref, containsInAnyOrder(containsString(validId)));

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("self"));

    List<Affordance> linkAffordances = links.stream().flatMap(l -> l.getAffordances().stream()).toList();
    assertEquals(3, linkAffordances.size());
  }

  @Test
  void shouldGenerateLinksForActivateCustomer() {

    Links links = this.customerLinkDiscoverabilityFactory.linksForActivateCustomer(validId);

    assertEquals(2, links.toList().size());

    List<String> linkHref = links.stream().map(l -> l.getHref()).toList();
    assertThat(linkHref, containsInAnyOrder(containsString(validId),containsString(validId)));

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("self", "deactivate"));

  }

  @Test
  void shouldGenerateLinksForDeactivateCustomer() {

    Links links = this.customerLinkDiscoverabilityFactory.linksForDeactivateCustomer(validId);

    assertEquals(2, links.toList().size());

    List<String> linkHref = links.stream().map(l -> l.getHref()).toList();
    assertThat(linkHref, containsInAnyOrder(containsString(validId),containsString(validId)));

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("self", "activate"));

  }

  @Test
  void shouldGenerateLinksForFindCustomerByName() {

    String name = "Foo Bar";
    String uriName = "Foo%20Bar";

    Links links = this.customerLinkDiscoverabilityFactory.linksForFindCustomerByName(name);

    assertEquals(1, links.toList().size());

    List<String> linkHref = links.stream().map(l -> l.getHref()).toList();
    assertThat(linkHref, containsInAnyOrder(containsString(uriName)));

    List<String> linkRels = links.stream().map(l -> l.getRel().toString()).toList();
    assertThat(linkRels, containsInAnyOrder("self"));

  }


  
}
