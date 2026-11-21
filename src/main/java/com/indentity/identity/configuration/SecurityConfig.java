package com.indentity.identity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {"/users", "/authentication/token", "/authentication/introspect"};

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(signerKey.getBytes(), "HS512");
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
        return  nimbusJwtDecoder;
    }
}