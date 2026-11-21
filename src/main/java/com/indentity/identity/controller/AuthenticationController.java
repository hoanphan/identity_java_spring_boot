package com.indentity.identity.controller;

import com.indentity.identity.dto.request.ApiResponse;
import com.indentity.identity.dto.request.AuthenticationRequest;
import com.indentity.identity.dto.request.IntrospectTokenRequest;
import com.indentity.identity.dto.response.AuthenticationResponse;
import com.indentity.identity.dto.response.IntrospectResponseResponse;
import com.indentity.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> athenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authentication = authenticationService.authentication(request);
        return  ApiResponse.<AuthenticationResponse>builder()
                .result(authentication)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponseResponse> introspect(@RequestBody IntrospectTokenRequest request) throws ParseException, JOSEException {
        IntrospectResponseResponse authentication = authenticationService.introspect(request);
        return  ApiResponse.<IntrospectResponseResponse>builder()
                .result(authentication)
                .build();
    }
}
