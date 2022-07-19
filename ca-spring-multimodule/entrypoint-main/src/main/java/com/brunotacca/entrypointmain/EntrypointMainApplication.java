package com.brunotacca.entrypointmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @SpringBootApplication(scanBasePackages = "com.brunotacca")
@SpringBootApplication
@ComponentScan(
  basePackages = {
    // "com.brunotacca", // works too, but it's not a good practice
    "com.brunotacca.entrypointmain",
    "com.brunotacca.domain.entities",
    "com.brunotacca.domain.usecases",
    "com.brunotacca.adapter.controllers",
    "com.brunotacca.adapter.gateways",
    "com.brunotacca.external.apis",
    "com.brunotacca.external.datasources",
  }
)
public class EntrypointMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntrypointMainApplication.class);
	}

}
