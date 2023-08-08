package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.account.Role;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class ChatroomResource {

    private final ChatroomRepository chatroomRepository;
    private final AccountRepository accountRepository;

    public ChatroomResource(ChatroomRepository chatroomRepository, AccountRepository accountRepository) {
        this.chatroomRepository = chatroomRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/chatroom")
    public ResponseEntity<String> createRoom(@RequestBody @Valid ChatroomRequestBody chatroomRequestBody) {

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepository.findByUsername(authentication.getName());
        if(account.isPresent()) {
            Chatroom chatroom =  new Chatroom(null, chatroomRequestBody.getName(), LocalDate.now(), account.get());

            chatroomRepository.save(chatroom);
            accountRepository.save(account.get());
            return new ResponseEntity<>("Successfully created room", HttpStatus.CREATED);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @GetMapping("/chatroom")
    public List<Chatroom> getRooms() {

        List<Chatroom> chatrooms = chatroomRepository.findAllOrderedByIdDesc();

        return chatrooms;
    }

    @GetMapping("/chatroom/{id}")
    public ResponseEntity<ChatroomResponse> getRoom(@PathVariable Integer id) {
        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);

        if(optionalChatroom.isPresent()) {
            Chatroom chatroom = optionalChatroom.get();
            List<Account> members = accountRepository.findAccountsByChatroomsId(id);
            System.out.println(members);
            ChatroomResponse chatroomResponse = new ChatroomResponse(chatroom, members.size());
            return new ResponseEntity<>(chatroomResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

//        return optionalChatroom.map(chatroom ->
//                    new ResponseEntity<>(new ChatroomResponse(chatroom, 0), HttpStatus.OK)
//                ).orElseGet(() ->
//                        new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
//                );
    }
}
