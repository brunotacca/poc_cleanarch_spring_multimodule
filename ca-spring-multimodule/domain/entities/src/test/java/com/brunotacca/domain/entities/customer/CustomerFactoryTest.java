package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
      assertNotSame("", validCustomer.getId());
      assertEquals(validName, validCustomer.getName());
      assertEquals(validEmail, validCustomer.getEmail());
      assertEquals(validAddress, validCustomer.getAddress());
    }

    @Test
    void shouldReconstituteAnExistingCustomer() throws BusinessException {
      CustomerFactory customerFactorySpied = Mockito.spy(new CustomerFactory());

      UUID expectedUuid = UUID.randomUUID();
      Customer validCustomer = customerFactorySpied.recreateExistingCustomer(expectedUuid, validName, validEmail, true, validAddress);

      assertNotNull(validCustomer);
      assertNotNull(validCustomer.getId());
      assertEquals(expectedUuid, validCustomer.getId());
      assertEquals(validName, validCustomer.getName());
      assertEquals(validEmail, validCustomer.getEmail());
      assertEquals(validAddress, validCustomer.getAddress());

      Mockito.verify(customerFactorySpied, times(1)).keepActiveValueForExistingCustomer(any(), eq(true));     
    }

    @Test 
    void shouldKeepActiveValueForExistingCustomer() throws BusinessException {
      Customer customerMock = Mockito.mock(Customer.class);

      customerFactory.keepActiveValueForExistingCustomer(customerMock, true);
      verify(customerMock, times(1)).activate();

      customerFactory.keepActiveValueForExistingCustomer(customerMock, false);
      verify(customerMock, times(1)).deactivate();

      Customer validCustomer = customerFactory.recreateExistingCustomer(UUID.randomUUID(), validName, validEmail, true, validAddress);
      Customer c1 = customerFactory.keepActiveValueForExistingCustomer(validCustomer, true);
      assertTrue(c1.isActive());
      Customer c2 = customerFactory.keepActiveValueForExistingCustomer(validCustomer, false);
      assertFalse(c2.isActive());
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
    void shouldThrowWhenReconstitutingWithNullId() {
      assertThrows(BusinessException.class, () -> {
        customerFactory.recreateExistingCustomer(null, validName, validEmail, null, validAddress);
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
