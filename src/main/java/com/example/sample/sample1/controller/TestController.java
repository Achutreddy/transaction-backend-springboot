package com.example.sample.sample1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/secret")
public class TestController {

    @GetMapping
    public ResponseEntity<?> getSecret(Principal principal){
        return (ResponseEntity.ok("Hello, "+principal.getName()+"! This is a JWT-protected endpoint."));
    }
}
