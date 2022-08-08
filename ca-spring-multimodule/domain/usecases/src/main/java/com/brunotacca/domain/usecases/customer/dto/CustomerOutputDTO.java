package com.brunotacca.domain.usecases.customer.dto;

public record CustomerOutputDTO(
  String id,
  String name,
  String email,
  Boolean active,
  String street,
  String number,
  String city,
  String zip
) {}
