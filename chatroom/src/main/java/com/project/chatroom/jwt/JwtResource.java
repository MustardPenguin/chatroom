package com.project.chatroom.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtResource {

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate() {

        return ResponseEntity.noContent().build();

    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}