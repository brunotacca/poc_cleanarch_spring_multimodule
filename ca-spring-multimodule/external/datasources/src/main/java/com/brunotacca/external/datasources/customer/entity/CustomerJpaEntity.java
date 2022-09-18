package com.brunotacca.external.datasources.customer.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerJpaEntity {
 
  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;
  
  private String name;
  private String email;
  private Boolean active;

  @Embedded
  private AddressJpaEntity address;

}
