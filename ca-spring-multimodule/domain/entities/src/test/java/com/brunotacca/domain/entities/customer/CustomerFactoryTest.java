package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CustomerFactoryTest {
  
  private final String validName = "Foo Bar";
  private final String validEmail = "foo@bar.com";
  private final Address validAddress = mock(Address.class, Mockito.RETURNS_DEEP_STUBS);
  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";

  @Nested
  class ForCustomerIt {
    @Test
    void shouldCreateCustomerWithId() {
      try {
        Customer validCustomer = CustomerFactory.createCustomer(validName, validEmail, validAddress);
    
        assertNotNull(validCustomer);
        assertNotNull(validCustomer.getId());
        assertNotSame("", validCustomer.getId().trim());
        assertEquals(validCustomer.getName(), validName);
        assertEquals(validCustomer.getEmail(), validEmail);
        assertEquals(validCustomer.getAddress(), validAddress);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  
    @Test
    void shouldThrowWhenCreatingCustomerWithInvalidProperties() {
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createCustomer(null, validEmail, validAddress);  
      });  
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createCustomer(validName, null, validAddress);  
      });  
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createCustomer(validName, validEmail, null);  
      });  
    }  
  }

  @Nested
  class ForAddressIt {
    @Test
    void shouldCreateAnAddress() {
      try {
        Address validAddress = CustomerFactory.createAddress(validStreet, validNumber, validZip, validCity);
    
        assertNotNull(validAddress);
        assertEquals(validAddress.getStreet(), validStreet);
        assertEquals(validAddress.getNumber(), validNumber);
        assertEquals(validAddress.getZip(), validZip);
        assertEquals(validAddress.getCity(), validCity);
      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  
    @Test
    void shouldThrowWhenCreatingAddressWithInvalidProperties() {
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createAddress(null, validNumber, validZip, validCity);
      });  
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createAddress(validStreet, null, validZip, validCity);
      });  
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createAddress(validStreet, validNumber, null, validCity);
      });  
      assertThrows(BusinessException.class, () -> {
        CustomerFactory.createAddress(validStreet, validNumber, validZip, null);
      });  
    }  
  }

}
