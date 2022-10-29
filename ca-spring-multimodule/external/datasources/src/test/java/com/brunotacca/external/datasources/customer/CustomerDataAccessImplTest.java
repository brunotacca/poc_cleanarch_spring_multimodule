package com.brunotacca.external.datasources.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;
import com.brunotacca.domain.usecases.shared.exceptions.causes.DataAccessException;
import com.brunotacca.external.datasources.CustomDisplayNameGenerator;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;
import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntityMapper;
import com.brunotacca.external.datasources.customer.repository.CustomerRepository;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerDataAccessImplTest {

  private CustomerDataAccess customerDataAccess;

  private CustomerRepository customerRepositoryMock = Mockito.mock(CustomerRepository.class);

  private CustomerJpaEntityMapper customerJpaEntityMapperMock = Mockito.mock(CustomerJpaEntityMapper.class);

  private final CustomerJpaFixtures customerJpaFixtureTestFactory = new CustomerJpaFixtures();

  private Customer validCustomer;
  private CustomerJpaEntity validCustomerJpa;
  private List<CustomerJpaEntity> validCustomerJpaList;

  @BeforeEach
  void setUp() throws BusinessException, DataAccessException {
    customerDataAccess = new CustomerDataAccessImpl(customerRepositoryMock, customerJpaEntityMapperMock);

    validCustomer = customerJpaFixtureTestFactory.getValidCustomer(true);
    validCustomerJpa  = customerJpaFixtureTestFactory.getValidCustomerJpa(true);
    validCustomerJpaList = List.of(validCustomerJpa, validCustomerJpa);

    doReturn(Optional.of(validCustomerJpa)).when(customerRepositoryMock).findById(any());
    doReturn(validCustomerJpaList).when(customerRepositoryMock).findByNameContainingIgnoreCase(anyString());
    doReturn(Optional.of(validCustomerJpa)).when(customerRepositoryMock).findByEmailIgnoreCase(anyString());

    doReturn(validCustomer).when(customerJpaEntityMapperMock).toDomainEntity(any());
    doReturn(validCustomerJpa).when(customerJpaEntityMapperMock).fromDomainEntity(any());
  }

  @Test
  void whenCreatingShouldCallPersistFlushWithMappedObject() throws DataAccessException, BusinessException {
    customerDataAccess.create(validCustomer);

    verify(customerJpaEntityMapperMock, times(1)).fromDomainEntity(validCustomer);
    verify(customerRepositoryMock, times(1)).persistAndFlush(validCustomerJpa);
  }

  @Test
  void whenCreatingShouldThrowIfDependenciesThrow() throws DataAccessException, BusinessException {
    doThrow(new RuntimeException("message")).when(customerJpaEntityMapperMock).fromDomainEntity(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.create(validCustomer));

    doThrow(new RuntimeException("message")).when(customerRepositoryMock).persistAndFlush(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.create(validCustomer));
  }

  @Test
  void whenUpdatingShouldCallMergeFlushWithMappedObject() throws DataAccessException, BusinessException {
    customerDataAccess.update(validCustomer);

    verify(customerJpaEntityMapperMock, times(1)).fromDomainEntity(validCustomer);
    verify(customerRepositoryMock, times(1)).mergeAndFlush(validCustomerJpa);
  }

  @Test
  void whenUpdatingShouldThrowIfDependenciesThrow() throws DataAccessException, BusinessException {
    doThrow(new RuntimeException("message")).when(customerJpaEntityMapperMock).fromDomainEntity(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.update(validCustomer));

    doThrow(new RuntimeException("message")).when(customerRepositoryMock).mergeAndFlush(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.update(validCustomer));
  }

  @Test
  void whenReadingShouldReturnMappedEntity() throws DataAccessException {
    Optional<Customer> customer = customerDataAccess.read(validCustomer.getId());

    verify(customerRepositoryMock, times(1)).findById(validCustomer.getId());
    verify(customerJpaEntityMapperMock, times(1)).toDomainEntity(any());
    assertNotNull(customer);
    assertTrue(customer.isPresent());
    assertEquals(validCustomer.getId(), customer.get().getId());
  }

  @Test
  void whenReadingShouldThrowIfDependenciesThrow() throws DataAccessException {
    doThrow(new RuntimeException("message")).when(customerRepositoryMock).findById(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.read(validCustomer.getId()));
  }

  @Test
  void whenFindingByNameShouldReturnListWithMappedEntities() throws DataAccessException {
    List<Customer> list = customerDataAccess.findByName(validCustomer.getName());
    
    verify(customerRepositoryMock, times(1)).findByNameContainingIgnoreCase(validCustomer.getName());
    verify(customerJpaEntityMapperMock, times(validCustomerJpaList.size())).toDomainEntity(any());
    assertNotNull(list);
    assertFalse(list.isEmpty());
  }

  @Test
  void whenFindingByNameShouldThrowIfDependenciesThrow() throws DataAccessException {
    doThrow(new RuntimeException("message")).when(customerJpaEntityMapperMock).toDomainEntity(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.findByName(validCustomer.getName()));

    doThrow(new RuntimeException("message")).when(customerRepositoryMock).findByNameContainingIgnoreCase(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.findByName(validCustomer.getName()));
  }

  @Test
  void whenFindingByEmailShouldReturnMappedEntity() throws DataAccessException {
    Optional<Customer> customer = customerDataAccess.findByEmail(validCustomer.getEmail());
    
    verify(customerRepositoryMock, times(1)).findByEmailIgnoreCase(validCustomer.getEmail());
    verify(customerJpaEntityMapperMock, times(1)).toDomainEntity(any());
    assertNotNull(customer);
    assertTrue(customer.isPresent());
    assertEquals(validCustomer.getId(), customer.get().getId());
  }

  @Test
  void whenFindingByEmailShouldThrowIfDependenciesThrow() throws DataAccessException {
    doThrow(new RuntimeException("message")).when(customerJpaEntityMapperMock).toDomainEntity(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.findByEmail(validCustomer.getEmail()));

    doThrow(new RuntimeException("message")).when(customerRepositoryMock).findByEmailIgnoreCase(any());
    assertThrows(DataAccessException.class, () -> customerDataAccess.findByEmail(validCustomer.getEmail()));
  }

}
