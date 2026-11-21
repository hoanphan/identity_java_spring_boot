package com.indentity.identity.service;

import com.indentity.identity.dto.request.AuthenticationRequest;
import com.indentity.identity.dto.request.IntrospectTokenRequest;
import com.indentity.identity.dto.response.AuthenticationResponse;
import com.indentity.identity.dto.response.IntrospectResponseResponse;
import com.indentity.identity.exception.AppException;
import com.indentity.identity.exception.ErrorCode;
import com.indentity.identity.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

   public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USE_NOT_EXITED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated)
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(authenticationRequest.getUsername());
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public IntrospectResponseResponse introspect(IntrospectTokenRequest introspectTokenRequest) throws JOSEException, ParseException {
       var token = introspectTokenRequest.getToken();
       JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
       var verified = signedJWT.verify(verifier);
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponseResponse.builder().isValid(verified && expiredTime.after(new Date())).build();
    }
    private String generateToken(String userName) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("dev.identity.com")
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("custom1", "value1")
                .claim("custom2", "value2")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SINGER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create");
            throw new RuntimeException(e);
        }
    }
}
