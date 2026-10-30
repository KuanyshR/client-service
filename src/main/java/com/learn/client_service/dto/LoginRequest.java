package com.learn.client_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credentials sent to {@code POST /auth/token}.
 */
@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;

    private String password;
}
