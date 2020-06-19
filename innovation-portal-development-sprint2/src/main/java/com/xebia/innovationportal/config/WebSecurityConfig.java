package com.xebia.innovationportal.config;

import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.Management.USER_UPDATE_STATUS_URI;
import static com.xebia.innovationportal.constants.RestConstants.Management.USER_URI;
import static com.xebia.innovationportal.constants.RestConstants.UserDetails.USER_AUTH_TOKEN_VERIFY;
import static com.xebia.innovationportal.constants.RestConstants.UserDetails.USER_LOGOUT;
import static com.xebia.innovationportal.constants.RestConstants.UserDetails.USER_OTP;
import static com.xebia.innovationportal.constants.RestConstants.UserDetails.USER_OTP_VERIFY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.innovationportal.constants.SecurityConstant;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.filters.JWTAuthenticationFilter;
import com.xebia.innovationportal.filters.JWTAuthorizationFilter;
import com.xebia.innovationportal.models.BaseResponse;
import com.xebia.innovationportal.services.JWTService;
import com.xebia.innovationportal.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JWTService jwtService;

    public WebSecurityConfig(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests().antMatchers(HttpMethod.POST, INNOVATION_PORTAL_API + USER_OTP).permitAll()
                .antMatchers(HttpMethod.GET, INNOVATION_PORTAL_API + USER_OTP_VERIFY).permitAll()
                .antMatchers(HttpMethod.GET, INNOVATION_PORTAL_API + USER_AUTH_TOKEN_VERIFY).permitAll()
                .antMatchers(HttpMethod.GET, INNOVATION_PORTAL_API + USER_URI).permitAll()
                .antMatchers(HttpMethod.PUT, INNOVATION_PORTAL_API + USER_UPDATE_STATUS_URI).permitAll().anyRequest()
                .authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userService, jwtService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .and().logout().logoutSuccessHandler(logoutSuccessHandler()).logoutUrl(USER_LOGOUT);

        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
                    HttpMethod.POST.name(), HttpMethod.PUT.name()));
            return corsConfiguration;
        });
        http.headers().frameOptions().disable(); // to overcome blank page issue on h2-console login

        http.httpBasic();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchy;
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json");
            response.setStatus(403);
            String resp = "{\"message\":\"access denied\"}";
            response.getWriter().write(resp);
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            BaseResponse<Void> resp = new BaseResponse<>(Collections.emptyList());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String header = request.getHeader(SecurityConstant.HEADER_AUTH);
            String token = StringUtils.replace(header, SecurityConstant.TOKEN_PREFIX, StringUtils.EMPTY);
            try {
                jwtService.deleteByJti(token);
                response.setStatus(200);
                response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            } catch (GenericException e) {
                resp.setCode(e.getCode());
                resp.setMessage(e.getMessage());
                try {
                    response.setStatus(400);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
