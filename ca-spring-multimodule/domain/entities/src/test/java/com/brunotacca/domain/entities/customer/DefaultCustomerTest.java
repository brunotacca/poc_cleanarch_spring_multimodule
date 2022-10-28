package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

import java.util.UUID;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class DefaultCustomerTest {

  private final CustomerFixtures customerFixtures = new CustomerFixtures(); 

  private String validName = CustomerFixtures.VALID_NAME;
  private String validEmail = CustomerFixtures.VALID_EMAIL;
  private Address validAddress = mock(Address.class, Mockito.RETURNS_DEEP_STUBS);
  private Customer validCustomer;

  @BeforeEach
  void beforeEach() throws BusinessException {
    this.validCustomer = customerFixtures.getValidCustomer(true);
  }

  @Test
  void shouldCreateValidCustomer() throws BusinessException {
    Customer created = new DefaultCustomer(UUID.randomUUID(), validName, validEmail, validAddress);

    assertNotNull(created);
    assertNotNull(created.getId());
    assertEquals(validName, created.getName());
    assertEquals(validEmail, created.getEmail());
    assertEquals(validAddress, created.getAddress());
}

  @Nested
  class CustomerValidationShouldThrow {

    @Test
    void ifIdIsNull() {
      assertThrows(RequiredFieldException.class, () -> {
        new DefaultCustomer(null, validName, validEmail, validAddress);
      });
    }

    @Test
    void ifNameIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeName(null);
      });
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeName("");
      });
    }

    @Test
    void ifEmailIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeEmail(null);
      });
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeEmail("");
      });
    }

    @Test
    void ifAddressIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeAddress(null);
      });
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeAddress(new Address(null, null, null, null));
      });
    }

  }

  @Nested
  class CustomerPropertiesShouldChange {

    @Test
    void withValidName() throws BusinessException {
      String newValidName = "Bruno Tacca";
      Customer c = validCustomer.changeName(newValidName);
      assertEquals(c.getName(), newValidName);

      Customer c2 = c.changeName(newValidName);
      assertEquals(c, c2);
    }

    @Test
    void withValidEmail() throws BusinessException {
      String newValidEmail = "brunotacca@gmail.com";
      Customer c = validCustomer.changeEmail(newValidEmail);
      assertEquals(c.getEmail(), newValidEmail);

      Customer c2 = c.changeEmail(newValidEmail);
      assertEquals(c, c2);
    }

    @Test
    void withValidAddress() throws BusinessException {
      Address newValidAddress = new Address("new street", "123-ABC", "00000-000FF", "The city");
      Customer c = validCustomer.changeAddress(newValidAddress);
      assertEquals(c.getAddress(), newValidAddress);

      Customer c2 = c.changeAddress(newValidAddress);
      assertEquals(c, c2);
    }

    @Test
    void whenActivating() {
      Customer deactivated = validCustomer.deactivate();
      assertFalse(deactivated.isActive());

      Customer activated = deactivated.activate();
      assertTrue(activated.isActive());

      // Must return same instance
      Customer sameActivated = activated.activate();
      assertEquals(activated, sameActivated);
    }

    @Test
    void whenDeactivating() {
      Customer activated = validCustomer.activate();
      assertTrue(activated.isActive());

      Customer deactivated = activated.deactivate();
      assertFalse(deactivated.isActive());

      // Must return same instance
      Customer sameDeactivated = deactivated.deactivate();
      assertEquals(deactivated, sameDeactivated);
    }

  }

}
