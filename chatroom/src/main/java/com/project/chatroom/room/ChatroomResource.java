package com.project.chatroom.room;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
public class ChatroomResource {

    private ChatroomRepository chatroomRepository;

    public ChatroomResource(ChatroomRepository chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    @PostMapping("/chatroom")
    public ResponseEntity<String> createRoom(@RequestBody @Valid ChatroomRequestBody chatroomRequestBody) {

        Chatroom chatroom = new Chatroom(null, chatroomRequestBody.getName(), LocalDate.now());
        chatroomRepository.save(chatroom);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return new ResponseEntity<>("Successfully created room", HttpStatus.CREATED);
    }

    @GetMapping("/chatroom")
    public List<Chatroom> getRooms() {

        List<Chatroom> chatrooms = chatroomRepository.getChatroomsByDateCreated();

        return chatrooms;
    }
}
