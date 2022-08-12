package com.brunotacca.entrypointmain.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brunotacca.adapter.controllers.CustomerController;
import com.brunotacca.domain.usecases.customer.CustomerUseCaseFactory;
import com.brunotacca.domain.usecases.dataaccess.CustomerDataAccess;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdapterConfiguration {

  private final CustomerDataAccess customerDataAccess;

  @Bean
  public CustomerUseCaseFactory customerUseCaseFactory() {
    return new CustomerUseCaseFactory(customerDataAccess);
  }

  @Bean
  public CustomerController customerController() {
    return new CustomerController(customerUseCaseFactory());
  }
  
}
