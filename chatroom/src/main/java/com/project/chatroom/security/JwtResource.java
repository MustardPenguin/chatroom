package com.project.chatroom.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtResource {

    @PostMapping("/authenticate")
    public void authenticate() {

    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
