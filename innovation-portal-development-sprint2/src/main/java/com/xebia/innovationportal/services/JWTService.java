package com.xebia.innovationportal.services;

import com.auth0.jwt.interfaces.Claim;
import com.xebia.innovationportal.entities.Jti;
import com.xebia.innovationportal.exceptions.GenericException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface JWTService {

    Jti save(Jti jti);

    String generate(String email);

    Map<String, Claim> decode(final String token);

    Jti findByValue(final String jti);

    String getJti(final String token);

    LocalDateTime getExpired(String token);

    String getEmail(String token);

    void deleteById(Long id);

    void deleteByJti(String token) throws GenericException;

    Optional<Jti> findByUserId(Long userId);

    Optional<String> createAndSaveAuthToken(final String email);

    boolean isValidJWTToken(final Jti jti);

}
