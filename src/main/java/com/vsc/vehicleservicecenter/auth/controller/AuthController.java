package com.vsc.vehicleservicecenter.auth.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtEncoder encoder;

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password
    ) {}

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        Instant now = Instant.now();

        String roles = auth.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .reduce((a, b) -> a + " " + b)
                .orElse("");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("vsc-local")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(auth.getName())
                .claim("roles", roles)
                .build();

        // Explicitly use HS256 for JWT signing
        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        String token = encoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();

        return Map.of(
                "tokenType", "Bearer",
                "accessToken", token
        );
    }
}