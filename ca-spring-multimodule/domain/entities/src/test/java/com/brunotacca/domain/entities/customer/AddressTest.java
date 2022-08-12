package com.brunotacca.domain.entities.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.CustomDisplayNameGenerator;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.entities.shared.exceptions.causes.RequiredFieldException;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class AddressTest {

  private final String validStreet = "street";
  private final String validNumber = "123-A";
  private final String validZip = "000000-000";
  private final String validCity = "city";

  @Test
  void shouldCreateValidAddress() throws BusinessException {
    Address created = new Address(validStreet, validNumber, validZip, validCity);
    assertNotNull(created);
    assertEquals(validStreet, created.getStreet());
    assertEquals(validNumber, created.getNumber());
    assertEquals(validZip, created.getZip());
    assertEquals(validCity, created.getCity());
  }
  

  @Nested
  class AddressValidationShouldThrow {

    @Test
    void ifStreetIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        new Address(null, validNumber, validZip, validCity);
      });  
      assertThrows(RequiredFieldException.class, () -> {
        new Address("", validNumber, validZip, validCity);
      });  
    }

    @Test
    void ifNumberIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, null, validZip, validCity);
      });  
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, "", validZip, validCity);
      });  
    }

    @Test
    void ifZipIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, validNumber, null, validCity);
      });  
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, validNumber, "", validCity);
      });  
    }

    @Test
    void ifCityIsInvalid() {
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, validNumber, validZip, null);
      });  
      assertThrows(RequiredFieldException.class, () -> {
        new Address(validStreet, validNumber, validZip, "");
      });  
    }

  }

}
