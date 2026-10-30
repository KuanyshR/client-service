package com.learn.client_service.dto;

import com.learn.client_service.entity.Customer;
import lombok.Builder;
import lombok.Value;

/**
 * Customer data returned to API clients.
 */
@Value
@Builder
public class CustomerResponse {

    Long id;

    String name;

    String email;

    /** Maps a {@link Customer} entity to its response DTO. */
    public static CustomerResponse from(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }
}
