package com.brunotacca.domain.usecases.customer.dto;

import java.util.UUID;

public record UpdateCustomerInputDTO(
  UUID id,
  String name,
  String email,
  String street,
  String number,
  String city,
  String zip
) {}
