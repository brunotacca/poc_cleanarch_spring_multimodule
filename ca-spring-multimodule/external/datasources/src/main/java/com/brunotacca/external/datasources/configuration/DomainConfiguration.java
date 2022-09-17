package com.brunotacca.external.datasources.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.external.datasources.annotation.ExcludeFromJacocoGeneratedReport;

@Configuration
@ExcludeFromJacocoGeneratedReport
public class DomainConfiguration {
  
  @Bean
  public CustomerFactory customerFactory() {
    return new CustomerFactory();
  }

}
