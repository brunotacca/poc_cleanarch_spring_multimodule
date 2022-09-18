package com.brunotacca.external.datasources.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntityMapper;
import com.brunotacca.external.datasources.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerDataAccessImpl implements CustomerDataAccess {

  private final CustomerRepository customerRepository;
  private final CustomerJpaEntityMapper customerJpaEntityMapper;

  @Override
  public void create(Customer c) throws DataAccessException {
    try {
      customerRepository.persistAndFlush(customerJpaEntityMapper.fromDomainEntity(c));
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public void update(Customer c) throws DataAccessException {
    try {
      customerRepository.mergeAndFlush(customerJpaEntityMapper.fromDomainEntity(c));
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public Optional<Customer> read(UUID id) throws DataAccessException {
    try {
      return optionalCustomerFromOptionalJpa(customerRepository.findById(id));
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public List<Customer> findByName(String name) throws DataAccessException {
    try {
      List<CustomerJpaEntity> customerJpaList = customerRepository.findByNameContainingIgnoreCase(name);

      List<Customer> customerList = new ArrayList<>();

      for (CustomerJpaEntity cJpa : customerJpaList)
        customerList.add(customerJpaEntityMapper.toDomainEntity(cJpa));

      return customerList;
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public Optional<Customer> findByEmail(String email) throws DataAccessException {
    try {
      return optionalCustomerFromOptionalJpa(customerRepository.findByEmailIgnoreCase(email));
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  private Optional<Customer> optionalCustomerFromOptionalJpa(Optional<CustomerJpaEntity> customerJpa) throws DataAccessException {

    Optional<Customer> customer = Optional.empty();
    if (customerJpa.isPresent()) {
      customer = Optional.of(customerJpaEntityMapper.toDomainEntity(customerJpa.get()));
    }

    return customer;
  }

}
