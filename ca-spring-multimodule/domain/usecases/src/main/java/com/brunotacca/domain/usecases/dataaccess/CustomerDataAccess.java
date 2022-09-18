package com.brunotacca.domain.usecases.dataaccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

public interface CustomerDataAccess {

  public void create(Customer c) throws DataAccessException;
  public Optional<Customer> read(UUID id) throws DataAccessException;
  public void update(Customer c) throws DataAccessException;
  
  public List<Customer> findByName(String name) throws DataAccessException;
  public Optional<Customer> findByEmail(String email) throws DataAccessException;
  
}
