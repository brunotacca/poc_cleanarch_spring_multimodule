package com.brunotacca.domain.usecases.dataaccess;

import java.util.List;

import com.brunotacca.domain.entities.customer.Customer;

public abstract class CustomerDataAccess {

  public abstract void save(Customer c);
  public abstract Customer read(String id);
  public abstract Customer findByEmail(String email);
  public abstract List<Customer> readAll();
  
}
