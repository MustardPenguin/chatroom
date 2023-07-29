package com.project.chatroom.room;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatroomResource {

    @PostMapping("/chatroom")
    public ResponseEntity<String> createRoom() {

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.noContent().build();
    }
}
