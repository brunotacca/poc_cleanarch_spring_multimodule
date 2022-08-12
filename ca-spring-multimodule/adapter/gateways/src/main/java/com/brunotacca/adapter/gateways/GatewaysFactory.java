package com.brunotacca.adapter.gateways;

import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;

public class GatewaysFactory {
 
  final CustomerDataAccess customerRepository = new FakeCustomerRepository();

  public CustomerDataAccess getCustomerRepository() {
    return customerRepository;
  }

}
