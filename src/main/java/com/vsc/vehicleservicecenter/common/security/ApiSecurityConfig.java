package com.vsc.vehicleservicecenter.common.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.vsc.vehicleservicecenter.auth.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApiSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(AppUserRepository repo) {
        return username -> {
            var user = repo.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));

            return User.withUsername(user.getUsername())
                    .password(user.getPasswordHash())
                    .authorities("ROLE_" + user.getRole())
                    .disabled(!user.isEnabled())
                    .build();
        };
    }

    @Bean
    AuthenticationManager authenticationManager(
            UserDetailsService users,
            PasswordEncoder encoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(users);

        provider.setPasswordEncoder(encoder);

        return new ProviderManager(provider);
    }

    @Bean
    JwtEncoder jwtEncoder(
            @Value("${app.jwt.secret}") String secret) {

        SecretKey key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");

        return new NimbusJwtEncoder(
                new ImmutableSecret<>(key));
    }

    @Bean
    JwtDecoder jwtDecoder(
            @Value("${app.jwt.secret}") String secret) {

        SecretKey key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    @Bean
    SecurityFilterChain apiSecurity(HttpSecurity http)
            throws Exception {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            String roles = jwt.getClaimAsString("roles");

            if (roles == null || roles.isBlank()) {
                return List.<GrantedAuthority>of();
            }

            Collection<GrantedAuthority> authorities =
                    Arrays.stream(roles.split("\\s+"))
                            .map(role -> (GrantedAuthority)
                                    new SimpleGrantedAuthority(role))
                            .toList();

            return authorities;
        });

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/actuator/health")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/**")
                        .hasAnyRole(
                                "ADMIN", "ADVISOR", "MECHANIC")
                        .requestMatchers("/api/v1/**")
                        .hasAnyRole("ADMIN", "ADVISOR")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(converter)))
                .build();
    }
}