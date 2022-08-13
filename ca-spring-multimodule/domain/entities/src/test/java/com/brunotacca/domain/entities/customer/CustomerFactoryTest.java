package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.UUID;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerFactoryTest {

  private final String validName = "Foo Bar";
  private final String validEmail = "foo@bar.com";
  private final Address validAddress = mock(Address.class, Mockito.RETURNS_DEEP_STUBS);
  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";

  private final CustomerFactory customerFactory = new CustomerFactory();

  @Nested
  class ForCustomerIt {
    @Test
    void shouldCreateCustomerWithId() throws BusinessException {
      Customer validCustomer = customerFactory.createCustomer(validName, validEmail, validAddress);

      assertNotNull(validCustomer);
      assertNotNull(validCustomer.getId());
      assertNotSame("", validCustomer.getId().trim());
      assertEquals(validName, validCustomer.getName());
      assertEquals(validEmail, validCustomer.getEmail());
      assertEquals(validAddress, validCustomer.getAddress());
    }

    @Test
    void shouldReconstituteAnExistingCustomer() throws BusinessException {
      UUID expectedUuid = UUID.randomUUID();
      Customer validCustomer = customerFactory.getExistingCustomer(expectedUuid.toString(), validName, validEmail, validAddress);

      assertNotNull(validCustomer);
      assertNotNull(validCustomer.getId());
      assertNotSame("", validCustomer.getId().trim());
      assertEquals(expectedUuid.toString(), validCustomer.getId());
      assertEquals(validName, validCustomer.getName());
      assertEquals(validEmail, validCustomer.getEmail());
      assertEquals(validAddress, validCustomer.getAddress());
    }

    @Test
    void shouldThrowWhenCreatingCustomerWithInvalidProperties() {
      assertThrows(BusinessException.class, () -> {
        customerFactory.createCustomer(null, validEmail, validAddress);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.createCustomer(validName, null, validAddress);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.createCustomer(validName, validEmail, null);
      });
    }

    @Test
    void shouldThrowWhenReconstitutingWithInvalidId() {
      assertThrows(BusinessException.class, () -> {
        customerFactory.getExistingCustomer(null, validName, validEmail, validAddress);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.getExistingCustomer(" ", validName, validEmail, validAddress);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.getExistingCustomer("", validName, validEmail, validAddress);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.getExistingCustomer("invalid uuid pattern", validName, validEmail, validAddress);
      });
    }

  }

  @Nested
  class ForAddressIt {
    @Test
    void shouldCreateAnAddress() throws BusinessException {
      Address validAddress = customerFactory.createAddress(validStreet, validNumber, validZip, validCity);

      assertNotNull(validAddress);
      assertEquals(validAddress.getStreet(), validStreet);
      assertEquals(validAddress.getNumber(), validNumber);
      assertEquals(validAddress.getZip(), validZip);
      assertEquals(validAddress.getCity(), validCity);
    }

    @Test
    void shouldThrowWhenCreatingAddressWithInvalidProperties() {
      assertThrows(BusinessException.class, () -> {
        customerFactory.createAddress(null, validNumber, validZip, validCity);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.createAddress(validStreet, null, validZip, validCity);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.createAddress(validStreet, validNumber, null, validCity);
      });
      assertThrows(BusinessException.class, () -> {
        customerFactory.createAddress(validStreet, validNumber, validZip, null);
      });
    }
  }

}
