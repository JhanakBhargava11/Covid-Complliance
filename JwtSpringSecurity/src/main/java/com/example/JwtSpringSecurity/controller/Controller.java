package com.example.JwtSpringSecurity.controller;

import com.example.JwtSpringSecurity.models.*;
import com.example.JwtSpringSecurity.service.MyUserDetailsService;
import com.example.JwtSpringSecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class Controller {
    @Autowired
    private TempUsername tempUsername;

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

    @PostMapping("/username")
    public ResponseEntity<?> verifyUsername(@RequestBody Username username) throws Exception{
        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(username.getUsername());
        }
        catch(NoSuchElementException e){
            throw new Exception("Incorrect username",e);
        }
        tempUsername.setUsername(username.getUsername());
        return ResponseEntity.ok("username Verified");
    }

    @PostMapping("/password")
    public ResponseEntity<?> verifyPassword(@RequestBody Password password) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tempUsername.getUsername(),password.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect password",e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(tempUsername.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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
