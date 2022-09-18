package com.brunotacca.external.datasources;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
  basePackages={
    "com.vladmihalcea.spring.repository",
    "com.brunotacca.external.datasources",
  }
)
public class DataSourcesTestApplication {
  
}
