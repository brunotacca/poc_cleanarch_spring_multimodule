package com.brunotacca.external.datasources.customer.entity;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;
import com.brunotacca.external.datasources.CustomDisplayNameGenerator;
import com.brunotacca.external.datasources.customer.CustomerJpaFixtureTestFactory;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerJpaEntityMapperTest {

  private final CustomerJpaFixtureTestFactory testCustomerValuesFactory = new CustomerJpaFixtureTestFactory();
  private final CustomerJpaEntityMapper customerJpaEntityMapper = new CustomerJpaEntityMapper(new CustomerFactory());

  @Test
  void shouldMapFromDomainEntityToJpaEntity() throws Exception {

    Customer validCustomer = testCustomerValuesFactory.getValidCustomer(false);

    CustomerJpaEntity customerJpa = customerJpaEntityMapper.fromDomainEntity(validCustomer);

    assertNotNull(customerJpa);
    assertEquals(validCustomer.getId(), customerJpa.getId());
    assertEquals(validCustomer.getName(), customerJpa.getName());
    assertEquals(validCustomer.getEmail(), customerJpa.getEmail());
    assertEquals(validCustomer.isActive(), customerJpa.getActive());
    assertNotNull(customerJpa.getAddress());
    assertEquals(validCustomer.getAddress().getStreet(), customerJpa.getAddress().getStreet());
    assertEquals(validCustomer.getAddress().getNumber(), customerJpa.getAddress().getNumber());
    assertEquals(validCustomer.getAddress().getZip(), customerJpa.getAddress().getZip());
    assertEquals(validCustomer.getAddress().getCity(), customerJpa.getAddress().getCity());

  }

  @Test
  void shouldThrowErrorWhenMappingFromDomainWithInvalidData() {
    assertThrows(Exception.class, ()-> customerJpaEntityMapper.fromDomainEntity(null));
  }

  @Test
  void shouldMapToDomainEntityFromJpaEntity() throws Exception {

    CustomerJpaEntity validCustomerJpa = testCustomerValuesFactory.getValidCustomerJpa(true);
    Customer customer = customerJpaEntityMapper.toDomainEntity(validCustomerJpa);

    assertNotNull(customer);
    assertEquals(validCustomerJpa.getId(), customer.getId());
    assertEquals(validCustomerJpa.getName(), customer.getName());
    assertEquals(validCustomerJpa.getEmail(), customer.getEmail());
    assertEquals(validCustomerJpa.getActive(), customer.isActive());
    assertNotNull(validCustomerJpa.getAddress());
    assertEquals(validCustomerJpa.getAddress().getStreet(), customer.getAddress().getStreet());
    assertEquals(validCustomerJpa.getAddress().getNumber(), customer.getAddress().getNumber());
    assertEquals(validCustomerJpa.getAddress().getZip(), customer.getAddress().getZip());
    assertEquals(validCustomerJpa.getAddress().getCity(), customer.getAddress().getCity());
  }

  @Test
  void shouldThrowErrorWhenMappingToDomainWithInvalidData() throws Exception {

    CustomerJpaEntity invalidCustomerJpa = testCustomerValuesFactory.getInvalidCustomerJpa();
    assertThrows(DataAccessException.class, () -> customerJpaEntityMapper.toDomainEntity(invalidCustomerJpa));

  }

}
