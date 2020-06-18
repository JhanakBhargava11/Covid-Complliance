package com.xebia.innovationportal.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.xebia.innovationportal.entities.Jti;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.repositories.JwtRepository;
import com.xebia.innovationportal.services.JWTService;
import com.xebia.innovationportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.xebia.innovationportal.constants.ExceptionConstants.TOKEN_INVALID;
import static com.xebia.innovationportal.constants.SecurityConstant.EXPIRATION_TIME;
import static com.xebia.innovationportal.constants.SecurityConstant.SECRET;
import static com.xebia.innovationportal.utils.Utils.convertToLocalDateTimeViaInstant;

@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Jti save(Jti jti) {
        return jwtRepository.save(jti);
    }

    @Transactional
    public String generate(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withJWTId(UUID.randomUUID().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public Map<String, Claim> decode(final String token) {
        return JWT.decode(token).getClaims();
    }

    @Transactional(readOnly = true)
    public Jti findByValue(final String jti) {
        return jwtRepository.findByValue(jti);
    }

    public String getJti(final String token) {
        Map<String, Claim> decode = decode(token);
        return decode.get("jti").asString();
    }

    public LocalDateTime getExpired(String token) {
        Map<String, Claim> decode = decode(token);
        Date exp = decode.get("exp").asDate();
        return convertToLocalDateTimeViaInstant(exp);
    }

    public String getEmail(String token) {
        Map<String, Claim> decode = decode(token);
        return decode.get("sub").asString();
    }

    @Transactional
    public void deleteById(Long id) {
        jwtRepository.deleteById(id);
    }

    @Transactional
    public void deleteByJti(String token) throws GenericException {
        String jti = getJti(token);
        Jti entity = jwtRepository.findByValue(jti);
        if (null == entity) throw new GenericException(TOKEN_INVALID, 400);
        jwtRepository.deleteByValue(jti);
    }

    @Transactional(readOnly = true)
    public Optional<Jti> findByUserId(Long userId) {
        return jwtRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Optional<String> createAndSaveAuthToken(final String email) {
        User user = userService.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));

        Jti jtiEntity;

        Optional<Jti> jti = findByUserId(user.getId());
        if (jti.isPresent())
            jtiEntity = jti.get();
        else
            jtiEntity = new Jti();

        String token = generate(user.getEmail());
        String id = getJti(token);
        LocalDateTime expired = getExpired(token);
        jtiEntity.setExpiredOn(expired);
        jtiEntity.setValue(id);
        jtiEntity.setCreatedByUser(user.getEmail());
        jtiEntity.setModifiedByUser(user.getEmail());
        jtiEntity.setUser(user);

        jwtRepository.save(jtiEntity);

        return Optional.of(token);
    }

    @Override
    public boolean isValidJWTToken(Jti jti) {
        if (jti.getExpiredOn().compareTo(LocalDateTime.now()) >= 0) {
            return true;
        }

        return false;
    }
}
