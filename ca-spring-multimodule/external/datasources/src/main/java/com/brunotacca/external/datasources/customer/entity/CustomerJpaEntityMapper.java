package com.brunotacca.external.datasources.customer.entity;

import org.springframework.stereotype.Component;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerJpaEntityMapper {

  private final CustomerFactory customerFactory;

  public CustomerJpaEntity fromDomainEntity(Customer c) {
    return new CustomerJpaEntity(
      c.getId(), 
      c.getName(), 
      c.getEmail(), 
      c.isActive(),
      new AddressJpaEntity(
        c.getAddress().getStreet(),
        c.getAddress().getNumber(), 
        c.getAddress().getZip(), 
        c.getAddress().getCity()
      )
    );
  }

  public Customer toDomainEntity(CustomerJpaEntity cJpa) throws DataAccessException {
    try {
      return customerFactory.recreateExistingCustomer(
        cJpa.getId(), 
        cJpa.getName(), 
        cJpa.getEmail(), 
        cJpa.getActive(), 
        customerFactory.createAddress(
          cJpa.getAddress().getStreet(), 
          cJpa.getAddress().getNumber(), 
          cJpa.getAddress().getZip(), 
          cJpa.getAddress().getCity()
        )
      );
    } catch (BusinessException e) {
      throw new DataAccessException(e.getMessage());
    }
  }

}
