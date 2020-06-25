package com.example.JwtSpringSecurity.controller;

import com.example.JwtSpringSecurity.service.MyUserDetailsService;
import com.example.JwtSpringSecurity.models.AuthenticationRequest;
import com.example.JwtSpringSecurity.models.AuthenticationResponse;
import com.example.JwtSpringSecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping({"/user"})
    public String user(){
        return "User";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping({"/admin"})
    public String admin(){
        return "Admin";
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
