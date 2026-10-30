package com.learn.client_service.security;

import com.learn.client_service.dto.LoginRequest;
import com.learn.client_service.dto.TokenResponse;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login endpoint: exchanges username and password for a JWT access token.
 * Counts logins in the metric {@code auth.logins} (Prometheus: {@code auth_logins_total{result=...}}).
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final MeterRegistry meterRegistry;

    /** Returns a Bearer token on valid credentials, or 401 otherwise. */
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            request.getUsername(), request.getPassword()));
            meterRegistry.counter("auth.logins", "result", "success").increment();
            return ResponseEntity.ok(new TokenResponse(
                    tokenService.issue(authentication), "Bearer", tokenService.lifetimeSeconds()));
        } catch (AuthenticationException e) {
            meterRegistry.counter("auth.logins", "result", "failure").increment();
            return ResponseEntity.status(401).build();
        }
    }
}
