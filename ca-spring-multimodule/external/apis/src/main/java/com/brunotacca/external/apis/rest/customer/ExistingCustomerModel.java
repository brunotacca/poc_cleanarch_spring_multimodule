package com.brunotacca.external.apis.rest.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ExistingCustomerModel extends RepresentationModel<ExistingCustomerModel> {
  @NotBlank
  private final String id;
  @NotBlank
  private final String name;
  @NotBlank
  private final String email;
  @NotNull
  private final Boolean active;
  @NotBlank
  private final String street;
  @NotBlank
  private final String number;
  @NotBlank
  private final String city;
  @NotBlank
  private final String zip;
}
