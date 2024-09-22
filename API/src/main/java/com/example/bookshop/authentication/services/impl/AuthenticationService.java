package com.example.bookshop.authentication.services.impl;

import com.example.bookshop.authentication.controllers.dto.token.AuthenticationRequest;
import com.example.bookshop.authentication.controllers.dto.token.IntrospectRequest;
import com.example.bookshop.authentication.controllers.dto.token.LogoutRequest;
import com.example.bookshop.authentication.controllers.dto.token.RefreshRequest;
import com.example.bookshop.authentication.controllers.dto.token.AuthenticationResponse;
import com.example.bookshop.authentication.controllers.dto.token.IntrospectResponse;
import com.example.bookshop.authentication.models.InvalidatedToken;
import com.example.bookshop.authentication.services.IAuthenticationService;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.authentication.repositories.InvalidatedTokenRepository;
import com.example.bookshop.users.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
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
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected String VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected String REFRESHABLE_DURATION;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new CustomRunTimeException(ErrorCode.PASSWORD_INCORRECT);
        }

        var token = generateToken(user);


        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }


    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (CustomRunTimeException e) {
            isValid = false;

        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryDate(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();

    }

    @Override
    public void logout(LogoutRequest logout) throws ParseException, JOSEException {
        try {
            var token = verifyToken(logout.getToken(), true);


            String jit = token.getJWTClaimsSet().getJWTID();
            Date expiration = token.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryDate(expiration)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (CustomRunTimeException e) {
            log.error("Token already expired");
        }

    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = (isRefresh)
                ? new Date(signedJWT.
                getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(Long.parseLong(REFRESHABLE_DURATION), ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expiration.after(new Date())))
            throw new CustomRunTimeException(ErrorCode.UNAUTHENTICATED);


        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new CustomRunTimeException(ErrorCode.UNAUTHENTICATED);
        }


        return signedJWT;
    }

    private String generateToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("bookshop.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(Long.parseLong(VALID_DURATION), ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.info("can not generate token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(UserEntity user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if (!user.getRole().toString().isEmpty()) {
                scopeJoiner.add("ROLE_" + user.getRole().toString().isEmpty());
        }
        return scopeJoiner.toString();
    }
}
