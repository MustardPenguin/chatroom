package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.account.Role;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class ChatroomResource {

    private final ChatroomRepository chatroomRepository;
    private final AccountRepository accountRepository;
    private final ChatroomUtil chatroomUtil;

    public ChatroomResource(ChatroomRepository chatroomRepository, AccountRepository accountRepository, ChatroomUtil chatroomUtil) {
        this.chatroomRepository = chatroomRepository;
        this.accountRepository = accountRepository;
        this.chatroomUtil = chatroomUtil;
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
    public ResponseEntity<Set<ChatroomResponse>> getRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
//        List<Chatroom> chatrooms = chatroomRepository.findAllOrderedByIdDesc();
        List<Chatroom> chatrooms = chatroomRepository.findChatroomByOwnerNotNullOrderByIdDesc(pageable);
        Set<ChatroomResponse> chatroomResponses = chatroomUtil.convertChatroomToChatroomResponse(chatrooms);

        return new ResponseEntity<>(chatroomResponses, HttpStatus.OK);
    }

    @GetMapping("/chatroom/{id}")
    public ResponseEntity<ChatroomResponse> getRoom(@PathVariable Integer id) {
        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);

        if(optionalChatroom.isPresent()) {
            Chatroom chatroom = optionalChatroom.get();
            List<Account> members = accountRepository.findAccountsByChatroomsId(id);
            ChatroomResponse chatroomResponse = new ChatroomResponse(chatroom, members.size());
            return new ResponseEntity<>(chatroomResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/chatroom/{id}")
    public ResponseEntity<String> joinRoom(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);
        Optional<Account> optionalAccount = accountRepository.findByUsername(authentication.getName());
        Account account;
        if(optionalAccount.isPresent()) {
            account = optionalAccount.get();
        } else {
            return new ResponseEntity<>("Error retrieving account data", HttpStatus.NOT_FOUND);
        }

        return optionalChatroom.map(chatroom -> {
            chatroom.getAccounts().add(account);
            account.getChatrooms().add(chatroom);
            accountRepository.save(account);
            return new ResponseEntity<>("Joined room", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Chatroom not found", HttpStatus.NOT_FOUND));
    }
}
