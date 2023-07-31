package com.project.chatroom.room;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatroomResource {

    private ChatroomRepository chatroomRepository;

    public ChatroomResource(ChatroomRepository chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    @PostMapping("/chatroom")
    public ResponseEntity<String> createRoom(@RequestBody @Valid ChatroomRequestBody chatroomRequestBody) {

        Chatroom chatroom = new Chatroom(null, chatroomRequestBody.getName());
        chatroomRepository.save(chatroom);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.noContent().build();
    }
}
