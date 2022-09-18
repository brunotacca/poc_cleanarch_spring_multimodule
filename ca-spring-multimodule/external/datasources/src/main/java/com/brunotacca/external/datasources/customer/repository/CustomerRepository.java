package com.brunotacca.external.datasources.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brunotacca.external.datasources.customer.entity.CustomerJpaEntity;
import com.vladmihalcea.spring.repository.HibernateRepository;

@Repository
public interface CustomerRepository extends HibernateRepository<CustomerJpaEntity>, JpaRepository<CustomerJpaEntity, UUID> {
 
  List<CustomerJpaEntity> findByNameContainingIgnoreCase(String name);

  Optional<CustomerJpaEntity> findByEmailIgnoreCase(String email);

}
