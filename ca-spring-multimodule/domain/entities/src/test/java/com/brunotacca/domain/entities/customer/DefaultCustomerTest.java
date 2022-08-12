package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class DefaultCustomerTest {

  private String validName = "Foo Bar";
  private String validEmail = "foo@bar.com";
  private Address validAddress = mock(Address.class, Mockito.RETURNS_DEEP_STUBS);
  private Customer validCustomer;

  @BeforeEach
  void beforeEach() throws BusinessException {
    this.validCustomer = new DefaultCustomer(UUID.randomUUID().toString(), validName, validEmail, validAddress);
  }

  @Nested
  class CustomerValidationShouldThrow {

    @Test
    void ifNameIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeName(null);
      });
      assertThrows(RequiredFieldException.class, () -> {
        validCustomer.changeEmail("");
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
    }

    @Test
    void withValidEmail() throws BusinessException {
      String newValidEmail = "brunotacca@gmail.com";
      Customer c = validCustomer.changeEmail(newValidEmail);
      assertEquals(c.getEmail(), newValidEmail);
    }

    @Test
    void withValidAddress() throws BusinessException {
      Address newValidAddress = new Address("street", "123-A", "00000-000", "city");
      Customer c = validCustomer.changeAddress(newValidAddress);
      assertEquals(c.getAddress(), newValidAddress);
    }

    @Test
    void whenActivatingOrDeactivating() {
      Customer c = validCustomer.deactivate();
      assertEquals(c.isActive(), false);
      Customer c2 = validCustomer.activate();
      assertEquals(c2.isActive(), true);
      Customer c3 = validCustomer.deactivate();
      assertEquals(c3.isActive(), false);
    }

  }

}
