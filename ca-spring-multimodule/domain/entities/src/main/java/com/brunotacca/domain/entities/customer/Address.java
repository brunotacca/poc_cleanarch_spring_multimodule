package com.brunotacca.domain.entities.customer;

import static com.brunotacca.domain.entities.shared.utils.ValidationUtils.*;

import com.brunotacca.domain.entities.shared.entities.BaseValueObject;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
class Address extends BaseValueObject {
  
  private final String street;
  private final String number;
  private final String zip;
  private final String city;

  protected Address(String street, String number, String zip, String city) throws BusinessException {
    this.street = street;
    this.number = number;
    this.zip = zip;
    this.city = city;
    this.validate();
  }

  @Override
  public void validate() throws BusinessException {
    if(isNullOrEmpty(this.street)) throw new RequiredFieldException("street");
    if(isNullOrEmpty(this.number)) throw new RequiredFieldException("number");
    if(isNullOrEmpty(this.zip)) throw new RequiredFieldException("zip");
    if(isNullOrEmpty(this.city)) throw new RequiredFieldException("city");
  }
  
}
