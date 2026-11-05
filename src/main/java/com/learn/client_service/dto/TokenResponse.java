package com.learn.client_service.dto;

import lombok.Value;

/**
 * JWT access token returned after a successful login.
 */
@Value
public class TokenResponse {

    String accessToken;

    String tokenType;

    long expiresInSeconds;
}
