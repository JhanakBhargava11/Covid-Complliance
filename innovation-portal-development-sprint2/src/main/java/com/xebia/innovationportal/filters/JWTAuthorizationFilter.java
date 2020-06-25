package com.xebia.innovationportal.filters;

import static com.xebia.innovationportal.constants.SecurityConstant.HEADER_AUTH;
import static com.xebia.innovationportal.constants.SecurityConstant.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xebia.innovationportal.constants.SecurityConstant;
import com.xebia.innovationportal.entities.Jti;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.services.JWTService;
import com.xebia.innovationportal.services.UserService;

@Component
public class  JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    private final UserService userService;
    private final JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserService userService, JWTService jwtService) {
        super(authManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(HEADER_AUTH);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
            chain.doFilter(req, res);
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
            throw new UsernameNotFoundException("Bad Credentials");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTH);
        if (token != null) {
            // parse the token.
            String jti = JWT.require(Algorithm.HMAC512(SecurityConstant.SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, StringUtils.EMPTY)).getId();

            Jti entity = jwtService.findByValue(jti);
            User entityUser = entity.getUser();
            // String channel = request.getHeader(HEADER_CHANNEL);

            if (Objects.nonNull(entity)) {// && null != Channel.valueOf(channel)/* == entity.getChannel() */) {
                // User entityUser = entity.getUser();
                if (entityUser != null && entityUser.isEnabled()) {
                    UserDetails user = userService.loadUserByUsername(entityUser.getEmail());
                    return new UsernamePasswordAuthenticationToken(entityUser, null, user.getAuthorities());
                }
            }
            return null;
        }
        return null;
    }
}
