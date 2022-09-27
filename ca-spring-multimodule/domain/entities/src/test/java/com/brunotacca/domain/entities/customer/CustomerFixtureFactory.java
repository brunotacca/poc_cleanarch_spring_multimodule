package com.brunotacca.domain.entities.customer;

import java.util.UUID;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public class CustomerFixtureFactory {

  private final CustomerFactory customerFactory = new CustomerFactory();

  public static final UUID VALID_ID = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
  public static final String VALID_NAME = "Foo Bar";
  public static final String VALID_EMAIL = "foo@bar.com";
  public static final String VALID_STREET = "street";
  public static final String VALID_NUMBER = "123-A";
  public static final String VALID_ZIP = "000000-000";
  public static final String VALID_CITY = "city";

  public Customer getValidCustomer(boolean active) throws BusinessException {
    return customerFactory.recreateExistingCustomer(VALID_ID, VALID_NAME, VALID_EMAIL, active, getValidAddress());
  }

  public Address getValidAddress() throws BusinessException {
    return customerFactory.createAddress(VALID_STREET, VALID_NUMBER, VALID_ZIP, VALID_CITY);
  }


}
