package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    Random rand = new Random();
    int random_num = rand.nextInt(10000);
    OTP otp=new OTP(String.valueOf(random_num));

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(otp.getOtp())
                .roles("USER")
                .and()
                .withUser("user2")
                .password(otp.getOtp())
                .roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
