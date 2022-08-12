package com.brunotacca.domain.usecases.dataaccess;

import java.util.List;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

public abstract class CustomerDataAccess {

  public abstract void save(Customer c) throws DataAccessException;
  public abstract Customer read(String id) throws DataAccessException;
  public abstract Customer findByEmail(String email) throws DataAccessException;
  public abstract List<Customer> readAll() throws DataAccessException;
  
}
