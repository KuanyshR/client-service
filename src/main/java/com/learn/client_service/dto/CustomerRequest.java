package com.learn.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for creating or updating a customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String name;

    private String email;
}
