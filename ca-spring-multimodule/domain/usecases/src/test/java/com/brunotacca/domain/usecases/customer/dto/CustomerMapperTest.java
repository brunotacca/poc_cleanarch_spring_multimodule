package com.brunotacca.domain.usecases.customer.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerMapperTest {
    
    private final String validName = "Foo Bar";
    private final String validEmail = "foo@bar.com";
    private final String validStreet = "street";
    private final String validNumber = "123-A";
    private final String validZip = "000000-000";
    private final String validCity = "city";
    private Customer customerMock = mock(Customer.class, RETURNS_DEEP_STUBS);
    private Address addressMock = mock(Address.class, RETURNS_DEEP_STUBS);

    @Test
    void shouldMapCorrectlyToDTO() {
        when(customerMock.getId()).thenReturn("4N_1D");
        when(customerMock.getName()).thenReturn(validName);
        when(customerMock.getEmail()).thenReturn(validEmail);
        when(customerMock.getAddress()).thenReturn(addressMock);
        when(addressMock.getCity()).thenReturn(validCity);
        when(addressMock.getStreet()).thenReturn(validStreet);
        when(addressMock.getNumber()).thenReturn(validNumber);
        when(addressMock.getZip()).thenReturn(validZip);
        
        
        CustomerMapper mapper = new CustomerMapper();
        CustomerOutputDTO output = mapper.fromCustomer(customerMock);

        assertNotNull(output);
        assertEquals("4N_1D", output.id());
        assertEquals(validName, output.name());
        assertEquals(validEmail, output.email());
        assertEquals(validStreet, output.street());
        assertEquals(validCity, output.city());
        assertEquals(validNumber, output.number());
        assertEquals(validZip, output.zip());
    }


}
