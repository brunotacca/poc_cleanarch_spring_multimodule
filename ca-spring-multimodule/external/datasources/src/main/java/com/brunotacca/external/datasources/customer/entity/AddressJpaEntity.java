package com.brunotacca.external.datasources.customer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressJpaEntity {

  @Column(name = "address_street")
  private String street;

  @Column(name = "address_number")
  private String number;

  @Column(name = "address_zip")
  private String zip;
  
  @Column(name = "address_city")
  private String city;

}
