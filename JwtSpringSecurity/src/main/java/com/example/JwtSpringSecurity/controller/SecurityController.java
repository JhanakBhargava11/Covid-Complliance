package com.example.JwtSpringSecurity.controller;

import com.example.JwtSpringSecurity.entity.UserResponse;
import com.example.JwtSpringSecurity.models.*;
import com.example.JwtSpringSecurity.service.EmailService;
import com.example.JwtSpringSecurity.service.MyUserDetailsService;
import com.example.JwtSpringSecurity.service.TempUsername;
import com.example.JwtSpringSecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SecurityController {
    @Autowired
    private TempUsername tempUsername;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping({"/superadmin"})
    public String user(){
        return "SuperAdmin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping({"/admin"})
    public String admin(){
        return "Admin";
    }

    @PostMapping("/username")
    public ResponseEntity<UserResponse> verifyUsername(@RequestBody Username username) throws Exception{
        UserResponse userResponse=new UserResponse();
        try{
            userDetailsService.loadUserByUsername(username.getUsername());
            emailService.sendEmail(username.getUsername());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username.getUsername());
            tempUsername.setUsername(username.getUsername());
            userResponse.setMessage("user verified and mail send");
            userResponse.setStatus(HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
        catch(NoSuchElementException e){
            userResponse.setMessage("invalid user");
            userResponse.setStatus(HttpStatus.FORBIDDEN);
//            throw new Exception("Incorrect username",e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userResponse);
        }
    }

    @PostMapping("/password")
    public ResponseEntity<UserResponse> verifyPassword(@RequestBody Password password) throws Exception{
        UserResponse userResponse = new UserResponse();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tempUsername.getUsername(),password.getPassword()));
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(tempUsername.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            userResponse.setStatus(HttpStatus.OK);
            userResponse.setMessage("Otp verified");
            List<String> jwtList = Arrays.asList(jwt);
            userResponse.setPayload(jwtList);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }catch (BadCredentialsException e){
            userResponse.setStatus(HttpStatus.BAD_REQUEST);
            userResponse.setMessage("Invalid OTP");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }
    }
}
