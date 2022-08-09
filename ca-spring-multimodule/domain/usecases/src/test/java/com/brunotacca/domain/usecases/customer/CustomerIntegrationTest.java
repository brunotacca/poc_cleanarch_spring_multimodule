package com.brunotacca.domain.usecases.customer;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CustomerIntegrationTest {

    // test with CustomerFactory
    // TODO
    @Nested
    class DependencyWithCustomerFactory {
        @Test
        void shouldReturnValidStuff() {
    
        }

        @Test
        void shouldThrownWhenInvalidParameters() {
    
        }
    }
    
    // test with CustomerDataAccess
    // TODO
    @Nested
    class DependencyWithCustomerDataAccess {
    }

    // test with CustomerMapper
    // TODO
    @Nested
    class DependencyWithCustomerMapper {
    }
}
