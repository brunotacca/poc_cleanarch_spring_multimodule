package com.brunotacca.domain.usecases.customer.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.brunotacca.domain.entities.customer.Address;
import com.brunotacca.domain.entities.customer.Customer;
import com.brunotacca.domain.usecases.CustomDisplayNameGenerator;

@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomerMapperTest {
    
    private final UUID validId = UUID.fromString("2d58df20-58e4-4238-b988-3ba5134021d0");
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
        when(customerMock.getId()).thenReturn(validId);
        when(customerMock.getName()).thenReturn(validName);
        when(customerMock.getEmail()).thenReturn(validEmail);
        when(customerMock.isActive()).thenReturn(false);
        when(customerMock.getAddress()).thenReturn(addressMock);
        when(addressMock.getCity()).thenReturn(validCity);
        when(addressMock.getStreet()).thenReturn(validStreet);
        when(addressMock.getNumber()).thenReturn(validNumber);
        when(addressMock.getZip()).thenReturn(validZip);
        
        
        CustomerMapper mapper = new CustomerMapper();
        CustomerOutputDTO output = mapper.outputFromEntity(customerMock);

        assertNotNull(output);
        assertEquals(validId, output.id());
        assertEquals(validName, output.name());
        assertEquals(validEmail, output.email());
        assertFalse(output.active());
        assertEquals(validStreet, output.street());
        assertEquals(validCity, output.city());
        assertEquals(validNumber, output.number());
        assertEquals(validZip, output.zip());
    }


}
