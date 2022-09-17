package com.brunotacca.external.apis.rest.customers;

import java.util.UUID;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public class TestCustomerValuesFactory {

  private final CustomerFactory customerFactory = new CustomerFactory();

  public final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
  public final String validName = "Foo Bar";
  public final String validEmail = "foo@bar.com";
  public final String validStreet = "street";
  public final String validNumber = "123-A";
  public final String validZip = "000000-000";
  public final String validCity = "city";

  public Customer getValidCustomer(boolean active) throws BusinessException {
    return customerFactory.recreateExistingCustomer(validId, validName, validEmail, active, getValidAddress());
  }

  public Address getValidAddress() throws BusinessException {
    return customerFactory.createAddress(validStreet, validNumber, validZip, validCity);
  }


}
