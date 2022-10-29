package com.brunotacca.external.datasources.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.external.datasources.customer.CustomerJpaFixtures;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;

@DataJpaTest
@Testcontainers
@ActiveProfiles("postgresql-it")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CustomerRepositoryTest {

  private final CustomerJpaFixtures testCustomerValuesFactory = new CustomerJpaFixtures();
  private CustomerJpaEntity validCustomerJpa = testCustomerValuesFactory.getValidCustomerJpa(true);

  @Autowired
  private CustomerRepository customerRepository;
  
  @Test
  void shouldFindEmptyListAndOneEntryById() throws BusinessException {

    // Assert repository is empty
    List<CustomerJpaEntity> list = customerRepository.findAll();
    assertTrue(list.isEmpty());

    // Assert repository is filled
    customerRepository.persistAndFlush(validCustomerJpa);
    list = customerRepository.findAll();
    assertFalse(list.isEmpty());
    assertEquals(1, list.size());

    // Assert entity found by id
    Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findById(validCustomerJpa.getId());
    assertNotNull(customerJpaEntity);
    assertTrue(customerJpaEntity.isPresent());
    assertEquals(validCustomerJpa.getName(), customerJpaEntity.get().getName());
  }

  @Test
  void shouldNotFindAnythingAndReturnEmpty() {
    customerRepository.persistAndFlush(validCustomerJpa);
    
    Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findByEmailIgnoreCase("non existing email");
    assertNotNull(customerJpaEntity);
    assertFalse(customerJpaEntity.isPresent());

    customerJpaEntity = customerRepository.findById(UUID.fromString("3644b314-36c0-11ed-a261-0242ac120002"));
    assertNotNull(customerJpaEntity);
    assertFalse(customerJpaEntity.isPresent());
  }

  @Test
  void shouldFindByEmailIgnoringCase() {
    customerRepository.persistAndFlush(validCustomerJpa);
    
    String upperCaseEmail = validCustomerJpa.getEmail().toUpperCase();
    String lowerCaseEmail = validCustomerJpa.getEmail().toLowerCase();
    Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findByEmailIgnoreCase(validCustomerJpa.getEmail());
    Optional<CustomerJpaEntity> customerJpaEntityUpper = customerRepository.findByEmailIgnoreCase(upperCaseEmail);
    Optional<CustomerJpaEntity> customerJpaEntityLower = customerRepository.findByEmailIgnoreCase(lowerCaseEmail);

    assertNotNull(customerJpaEntity);
    assertNotNull(customerJpaEntityUpper);
    assertNotNull(customerJpaEntityLower);

    assertTrue(customerJpaEntity.isPresent());
    assertTrue(customerJpaEntityUpper.isPresent());
    assertTrue(customerJpaEntityLower.isPresent());

    assertEquals(customerJpaEntity.get(), customerJpaEntityUpper.get());
    assertEquals(customerJpaEntity.get(), customerJpaEntityLower.get());
  }

  @Test
  void shouldFindByNameContainingAndIgnoringCase() {
    customerRepository.persistAndFlush(validCustomerJpa);

    String partialName = validCustomerJpa.getName().substring(1, validCustomerJpa.getName().length()-2);
    String upperCasePartialName = partialName.toUpperCase();
    String lowerCasePartialEmail = partialName.toLowerCase();

    List<CustomerJpaEntity> customerJpaEntityList = customerRepository.findByNameContainingIgnoreCase(partialName);
    List<CustomerJpaEntity> customerJpaEntityUpperList = customerRepository.findByNameContainingIgnoreCase(upperCasePartialName);
    List<CustomerJpaEntity> customerJpaEntityLowerList = customerRepository.findByNameContainingIgnoreCase(lowerCasePartialEmail);

    assertNotNull(customerJpaEntityList);
    assertNotNull(customerJpaEntityUpperList);
    assertNotNull(customerJpaEntityLowerList);
    assertFalse(customerJpaEntityList.isEmpty());
    assertFalse(customerJpaEntityUpperList.isEmpty());
    assertFalse(customerJpaEntityLowerList.isEmpty());

    assertEquals(1, customerJpaEntityList.size());
    assertEquals(1, customerJpaEntityUpperList.size());
    assertEquals(1, customerJpaEntityLowerList.size());

    assertEquals(validCustomerJpa.getName(), customerJpaEntityList.get(0).getName());
    assertEquals(validCustomerJpa.getName(), customerJpaEntityUpperList.get(0).getName());
    assertEquals(validCustomerJpa.getName(), customerJpaEntityLowerList.get(0).getName());
  }

}
