package com.example.JwtSpringSecurity.service;

import com.example.JwtSpringSecurity.entity.User;
import com.example.JwtSpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=repository.findByUsername(username);
        System.out.println("user = "+user.map(MyUserDetails::new).get());
        return user.map(MyUserDetails::new).get();
    }

//    public void verifyUsername(String username) {
//        Optional<User> user=repository.findByUsername(username);
//    }
}
