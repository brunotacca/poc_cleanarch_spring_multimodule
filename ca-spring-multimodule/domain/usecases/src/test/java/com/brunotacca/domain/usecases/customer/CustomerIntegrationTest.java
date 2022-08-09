package com.brunotacca.domain.usecases.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.entities.customer.CustomerFactory;
import com.brunotacca.domain.entities.shared.exceptions.BusinessException;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
public class CustomerIntegrationTest {

    private final String validName = "Foo Bar";
    private final String validEmail = "foo@bar.com";
    private final String validStreet = "street";
    private final String validNumber = "123-A";
    private final String validZip = "000000-000";
    private final String validCity = "city";

    CustomerFactory customerFactory = new CustomerFactory();

    @Nested
    class DependencyWithCustomerFactory {
        @Test
        void shouldReturnValidStuff() throws BusinessException {
            Address createdAddress = customerFactory.createAddress(validStreet, validNumber, validZip, validCity);
            assertNotNull(createdAddress);
            assertEquals(validStreet, createdAddress.getStreet());
            assertEquals(validNumber, createdAddress.getNumber());
            assertEquals(validZip, createdAddress.getZip());
            assertEquals(validCity, createdAddress.getCity());

            Customer createdCustomer = customerFactory.createCustomer(validName, validEmail, createdAddress);
            assertNotNull(createdCustomer);
            assertNotNull(createdCustomer.getId());
            assertEquals(validName, createdCustomer.getName());
            assertEquals(validEmail, createdCustomer.getEmail());
            assertEquals(validStreet, createdCustomer.getAddress().getStreet());
            assertEquals(validNumber, createdCustomer.getAddress().getNumber());
            assertEquals(validZip, createdCustomer.getAddress().getZip());
            assertEquals(validCity, createdCustomer.getAddress().getCity());
        }

        @Test
        void shouldThrownWhenInvalidParameters() throws BusinessException {
            Address validAddress = customerFactory.createAddress(validStreet, validNumber, validZip, validCity);

            assertThrows(BusinessException.class, () -> {
                customerFactory.createCustomer(null, validEmail, validAddress);
            });
            assertThrows(BusinessException.class, () -> {
                customerFactory.createCustomer(validName, null, validAddress);
            });
            assertThrows(BusinessException.class, () -> {
                customerFactory.createCustomer(validName, validEmail, null);
            });

            assertThrows(BusinessException.class, () -> {
                customerFactory.createAddress(null, validNumber, validZip, validCity);
            });
            assertThrows(BusinessException.class, () -> {
                customerFactory.createAddress(validStreet, null, validZip, validCity);
            });
            assertThrows(BusinessException.class, () -> {
                customerFactory.createAddress(validStreet, validNumber, null, validCity);
            });
            assertThrows(BusinessException.class, () -> {
                customerFactory.createAddress(validStreet, validNumber, validZip, null);
            });

        }
    }

}
