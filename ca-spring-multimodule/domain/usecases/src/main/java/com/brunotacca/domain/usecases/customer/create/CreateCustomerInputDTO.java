package com.brunotacca.domain.usecases.customer.create;

public record CreateCustomerInputDTO(
  String name,
  String email,
  String street,
  String number,
  String city,
  String zip
) {}
