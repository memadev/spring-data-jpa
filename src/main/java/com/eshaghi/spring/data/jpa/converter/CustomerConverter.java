package com.eshaghi.spring.data.jpa.converter;

import com.eshaghi.spring.data.jpa.domain.Customer;
import com.eshaghi.spring.data.jpa.dto.CustomerDto;
import java.util.List;
import java.util.stream.StreamSupport;

public class CustomerConverter {

    public static List<CustomerDto> convertToDtoList(Iterable<Customer> customers) {
        return StreamSupport.stream(customers.spliterator(), false)
                .map(CustomerConverter::convert)
                .toList();
    }

    public static CustomerDto convert(Customer customer) {
        return new CustomerDto(customer.getId(),
                customer.getPersonNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                AccountConverter.convertToDtoList(customer.getAccounts()));
    }

    public static Customer convertForAdd(CustomerDto dto) {
        return new Customer(0,
                dto.personNumber(),
                dto.firstName(),
                dto.lastName(),
                List.of());
    }

    public static Customer convertForUpdate(CustomerDto dto) {
        return new Customer(dto.id(),
                dto.personNumber(),
                dto.firstName(),
                dto.lastName(),
                AccountConverter.convertToList(dto.accounts()));
    }
}
