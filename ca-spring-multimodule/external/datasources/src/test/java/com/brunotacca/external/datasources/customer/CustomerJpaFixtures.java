package com.brunotacca.external.datasources.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFixtures;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.external.datasources.customer.entity.AddressJpaEntity;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;

public class CustomerJpaFixtures {

  private final CustomerFixtures customerFixtures = new CustomerFixtures();

  public final UUID validId = CustomerFixtures.VALID_ID;
  public final String validName = CustomerFixtures.VALID_NAME;
  public final String validEmail = CustomerFixtures.VALID_EMAIL;
  public final String validStreet = CustomerFixtures.VALID_STREET;
  public final String validNumber = CustomerFixtures.VALID_NUMBER;
  public final String validZip = CustomerFixtures.VALID_ZIP;
  public final String validCity = CustomerFixtures.VALID_CITY;

  public Customer getValidCustomer(boolean active) throws BusinessException {
    return customerFixtures.getValidCustomer(active);
  }

  public CustomerJpaEntity getValidCustomerJpa(boolean active) {
    return new CustomerJpaEntity(validId, validName, validEmail, active, new AddressJpaEntity(validStreet, validNumber, validZip, validCity));
  }

  public CustomerJpaEntity getInvalidCustomerJpa() {
    return new CustomerJpaEntity(validId, null, null, false, new AddressJpaEntity(validStreet, validNumber, validZip, validCity));
  }

  public Address getValidAddress() throws BusinessException {
    return customerFixtures.getValidAddress();
  }

}
