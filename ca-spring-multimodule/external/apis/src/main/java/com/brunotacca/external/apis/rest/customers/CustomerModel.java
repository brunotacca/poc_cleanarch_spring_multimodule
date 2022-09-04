package com.brunotacca.external.apis.rest.customers;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
class CustomerModel extends RepresentationModel<CustomerModel> {

  @NotBlank
  String name;
  @NotBlank
  String email;
  @NotBlank
  String street;
  @NotBlank
  String number;
  @NotBlank
  String city;
  @NotBlank
  String zip;
}
