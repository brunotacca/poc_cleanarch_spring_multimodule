package com.brunotacca.domain.usecases.customer.dto;

public record UpdateCustomerInputDTO(
  String id,
  String name,
  String email,
  String street,
  String number,
  String city,
  String zip
) {}
