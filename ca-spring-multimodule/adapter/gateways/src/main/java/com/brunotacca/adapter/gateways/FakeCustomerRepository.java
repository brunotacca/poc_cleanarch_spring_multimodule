package com.brunotacca.adapter.gateways;

import java.util.List;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

public class FakeCustomerRepository extends CustomerDataAccess  {

  @Override
  public void save(Customer c) throws DataAccessException {
    // TODO Auto-generated method stub
  }

  @Override
  public Customer read(String id) throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Customer findByEmail(String email) throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Customer> readAll() throws DataAccessException {
    // TODO Auto-generated method stub
    return null;
  }
  
}
