package com.example.bookshop.authentication.controllers;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.authentication.controllers.dto.token.AuthenticationRequest;
import com.example.bookshop.authentication.controllers.dto.token.IntrospectRequest;
import com.example.bookshop.authentication.controllers.dto.token.LogoutRequest;
import com.example.bookshop.authentication.controllers.dto.token.RefreshRequest;
import com.example.bookshop.authentication.controllers.dto.token.AuthenticationResponse;
import com.example.bookshop.authentication.controllers.dto.token.IntrospectResponse;
import com.example.bookshop.books.services.impl.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var resp = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(resp)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var resp = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(resp)
                .build();
    }
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var resp = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(resp)
                .build();
    }
    @PostMapping("/logout")
    ApiResponse<Void> authenticate(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }


}
