package com.github.guramkankava.rest.controllers;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public String hello(OAuth2Authentication authentication) {
        return "hello you " + authentication.getName();
    }

}
