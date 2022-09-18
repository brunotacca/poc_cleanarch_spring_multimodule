package com.brunotacca.external.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.brunotacca.external.apis.annotation.ExcludeFromJacocoGeneratedReport;

// @SpringBootApplication(scanBasePackages = "com.brunotacca")
@SpringBootApplication
@ComponentScan(
  basePackages = {
    "com.brunotacca.domain.entities",
    "com.brunotacca.domain.usecases",
    "com.brunotacca.adapter.controllers",
    "com.brunotacca.external.datasources",
    "com.brunotacca.external.apis",
  }
)
public class ApisApplication {

  @ExcludeFromJacocoGeneratedReport
	public static void main(String[] args) {
		SpringApplication.run(ApisApplication.class);
	}

}
