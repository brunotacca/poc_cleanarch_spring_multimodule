package com.brunotacca.domain.usecases.customer.create;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CreateCustomerUseCaseTest {

  void shouldReturnCustomerWithId() {
    
  }

  @Nested
  class CreatingCustomerShouldThrow {

    void withInvalidParameters() {

    }

    void withNotUniqueEmail() {

    }


  }
  
}
