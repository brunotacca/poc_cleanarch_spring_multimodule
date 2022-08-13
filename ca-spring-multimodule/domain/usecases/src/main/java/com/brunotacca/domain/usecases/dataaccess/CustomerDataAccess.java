package com.brunotacca.domain.usecases.dataaccess;

import java.util.List;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

public interface CustomerDataAccess {

  public void save(Customer c) throws DataAccessException;
  public Customer read(String id) throws DataAccessException;
  public Customer findByEmail(String email) throws DataAccessException;
  public List<Customer> readAll() throws DataAccessException;
  
}
