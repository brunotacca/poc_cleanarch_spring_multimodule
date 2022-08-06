package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CustomerFactoryTest {
  
  private String validName = "Foo Bar";
  private String validEmail = "foo@bar.com";
  private Address validAddress = mock(Address.class, Mockito.RETURNS_DEEP_STUBS);

  @Test
  void shouldCreateCustomerWithId() throws BusinessException {
    Customer validCustomer = CustomerFactory.create(validName, validEmail, validAddress);

    assertNotNull(validCustomer);
    assertNotNull(validCustomer.getId());
    assertNotSame("", validCustomer.getId().trim());
    assertEquals(validCustomer.getName(), validName);
    assertEquals(validCustomer.getEmail(), validEmail);
    assertEquals(validCustomer.getAddress(), validAddress);
  }

  @Test
  void shouldThrowWhenInvalidProperties() {
    assertThrows(BusinessException.class, () -> {
      CustomerFactory.create(null, validEmail, validAddress);  
    });  
    assertThrows(BusinessException.class, () -> {
      CustomerFactory.create(validName, null, validAddress);  
    });  
    assertThrows(BusinessException.class, () -> {
      CustomerFactory.create(validName, validEmail, null);  
    });  
  }


}
