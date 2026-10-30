package com.learn.client_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Creates signed JWT access tokens for authenticated users.
 */
@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final Duration lifetime;

    public TokenService(JwtEncoder jwtEncoder,
                        @Value("${app.jwt.expiration-minutes:60}") long expirationMinutes) {
        this.jwtEncoder = jwtEncoder;
        this.lifetime = Duration.ofMinutes(expirationMinutes);
    }

    /** Issues a token with the user name as subject and the user's roles in the {@code roles} claim. */
    public String issue(Authentication authentication) {
        Instant now = Instant.now();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.startsWith("ROLE_") ? a.substring("ROLE_".length()) : a)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("customer-service")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(lifetime))
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    /** Token lifetime in seconds, reported to the client. */
    public long lifetimeSeconds() {
        return lifetime.toSeconds();
    }
}
