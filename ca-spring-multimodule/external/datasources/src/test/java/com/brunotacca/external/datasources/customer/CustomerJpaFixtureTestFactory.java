package com.brunotacca.external.datasources.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFixtureFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.external.datasources.customer.entity.AddressJpaEntity;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;

public class CustomerJpaFixtureTestFactory {

  private final CustomerFixtureFactory customerFixtureFactory = new CustomerFixtureFactory();

  public final UUID validId = CustomerFixtureFactory.VALID_ID;
  public final String validName = CustomerFixtureFactory.VALID_NAME;
  public final String validEmail = CustomerFixtureFactory.VALID_EMAIL;
  public final String validStreet = CustomerFixtureFactory.VALID_STREET;
  public final String validNumber = CustomerFixtureFactory.VALID_NUMBER;
  public final String validZip = CustomerFixtureFactory.VALID_ZIP;
  public final String validCity = CustomerFixtureFactory.VALID_CITY;

  public Customer getValidCustomer(boolean active) throws BusinessException {
    return customerFixtureFactory.getValidCustomer(active);
  }

  public CustomerJpaEntity getValidCustomerJpa(boolean active) {
    return new CustomerJpaEntity(validId, validName, validEmail, active, new AddressJpaEntity(validStreet, validNumber, validZip, validCity));
  }

  public CustomerJpaEntity getInvalidCustomerJpa() {
    return new CustomerJpaEntity(validId, null, null, false, new AddressJpaEntity(validStreet, validNumber, validZip, validCity));
  }

  public Address getValidAddress() throws BusinessException {
    return customerFixtureFactory.getValidAddress();
  }

}
