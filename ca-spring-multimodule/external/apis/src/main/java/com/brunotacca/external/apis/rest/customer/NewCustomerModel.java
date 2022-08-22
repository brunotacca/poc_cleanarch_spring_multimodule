package com.brunotacca.external.apis.rest.customer;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class NewCustomerModel extends RepresentationModel<NewCustomerModel> {

  @NotBlank
  private final String name;
  @NotBlank
  private final String email;
  @NotBlank
  private final String street;
  @NotBlank
  private final String number;
  @NotBlank
  private final String city;
  @NotBlank
  private final String zip;
}
