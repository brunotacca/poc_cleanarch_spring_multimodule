package com.brunotacca.external.datasources.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.brunotacca.external.datasources.annotation.ExcludeFromJacocoGeneratedReport;

@Configuration
@PropertySource("classpath:/application.datasources.properties")
@EnableJpaRepositories(
  basePackages={
    "com.vladmihalcea.spring.repository",
    "com.brunotacca.external.datasources",
  }
)
@EntityScan("com.brunotacca.external.datasources")
@ExcludeFromJacocoGeneratedReport
public class DataSourcesConfiguration {
  
}
