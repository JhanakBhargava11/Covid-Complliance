package com.example.demo;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class Controller {

    @GetMapping("/")
    public String returnHello(){
        return "Hello";
    }


    @GetMapping("/user")
    public String user(Principal principal){
        System.out.println(principal.getName());
        return "<h1> Welcome User </h1>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h1> Welcome Admin </h1>";
    }
}

