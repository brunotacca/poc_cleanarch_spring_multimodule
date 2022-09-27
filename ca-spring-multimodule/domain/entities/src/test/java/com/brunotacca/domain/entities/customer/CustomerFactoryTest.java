package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerFactoryTest {

  private final CustomerFixtureFactory customerFixtureFactory = new CustomerFixtureFactory(); 

  private final String validName = CustomerFixtureFactory.VALID_NAME;
  private final String validEmail = CustomerFixtureFactory.VALID_EMAIL;
  private Address validAddress;
  private final String validStreet = CustomerFixtureFactory.VALID_STREET;
  private final String validNumber = CustomerFixtureFactory.VALID_NUMBER;
  private final String validZip = CustomerFixtureFactory.VALID_ZIP;
  private final String validCity = CustomerFixtureFactory.VALID_CITY;

  private final CustomerFactory customerFactory = new CustomerFactory();

  @BeforeEach
  void setUp() throws BusinessException {
    this.validAddress = customerFixtureFactory.getValidAddress();
  }

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

      Customer customerReturned = customerFactory.keepActiveValueForExistingCustomer(customerMock, null);
      assertEquals(customerMock, customerReturned);

      Customer validCustomer = customerFactory.recreateExistingCustomer(UUID.randomUUID(), validName, validEmail, true, validAddress);
      Customer c1 = customerFactory.keepActiveValueForExistingCustomer(validCustomer, true);
      assertTrue(c1.isActive());
      Customer c2 = customerFactory.keepActiveValueForExistingCustomer(validCustomer, false);
      assertFalse(c2.isActive());

      assertNotSame(customerMock, c1);
      assertNotSame(customerMock, c2);
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
