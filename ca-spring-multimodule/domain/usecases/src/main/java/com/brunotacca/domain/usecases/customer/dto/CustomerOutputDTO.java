package com.brunotacca.domain.usecases.customer.dto;

import java.util.UUID;

public record CustomerOutputDTO(
  UUID id,
  String name,
  String email,
  Boolean active,
  String street,
  String number,
  String city,
  String zip
) {}
