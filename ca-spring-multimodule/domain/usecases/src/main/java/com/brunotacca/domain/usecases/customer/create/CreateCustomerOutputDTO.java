package com.brunotacca.domain.usecases.customer.create;

public record CreateCustomerOutputDTO(
  String id,
  String name,
  String email,
  String street,
  String number,
  String city,
  String zip
) {}
