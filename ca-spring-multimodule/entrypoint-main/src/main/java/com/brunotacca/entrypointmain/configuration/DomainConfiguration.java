package com.brunotacca.entrypointmain.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brunotacca.adapter.gateways.FakeCustomerRepository;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;

@Configuration
public class DomainConfiguration {
  
  @Bean
  public CustomerDataAccess customerDataAccess() {
    return new FakeCustomerRepository();
  }

}
