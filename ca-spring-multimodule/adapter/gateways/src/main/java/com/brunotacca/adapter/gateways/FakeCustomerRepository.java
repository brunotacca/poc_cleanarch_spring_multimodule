package com.brunotacca.adapter.gateways;

import java.util.List;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

public class FakeCustomerRepository implements CustomerDataAccess  {

  @Override
  public void save(Customer c) throws DataAccessException {
    System.out.println("FakeRepository: save "+c);
  }

  @Override
  public Customer read(String id) throws DataAccessException {
    System.out.println("FakeRepository: read "+id);
    return this.getFakeCustomer(id);
  }

  @Override
  public Customer findByEmail(String email) throws DataAccessException {
    System.out.println("FakeRepository: findByEmail "+email);
    return null;
  }

  // @Override
  // public List<Customer> readAll() throws DataAccessException {
  //   System.out.println("FakeRepository: readAll");
  //   return List.of(getFakeCustomer("5142387a-053d-4bc9-989c-c9001984a34c"),getFakeCustomer("95253ac0-b022-43be-9883-263381b38e13"));
  // }

  @Override
  public List<Customer> findByName(String name) throws DataAccessException {
    System.out.println("FakeRepository: findByName "+name);
    return List.of(getFakeCustomer("5142387a-053d-4bc9-989c-c9001984a34c"),getFakeCustomer("95253ac0-b022-43be-9883-263381b38e13"));
  }

  private Customer getFakeCustomer(String id) {
    CustomerFactory customerFactory = new CustomerFactory();
    Customer c = null;
    try {
      c = customerFactory.getExistingCustomer(
                  id, "fake_name", "fake_email", 
                  customerFactory.createAddress("fake_street", "fake_number", "fake_zip", "fake_city")
              );
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }
    return c;
  }
  
}
