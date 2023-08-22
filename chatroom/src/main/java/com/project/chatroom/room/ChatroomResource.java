package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.account.Role;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import java.util.stream.Collectors;

@RestController
public class ChatroomResource {

    private final ChatroomRepository chatroomRepository;
    private final AccountRepository accountRepository;
    private final ChatroomUtil chatroomUtil;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int MAX_CHATROOM_PER_USER = 15;
    private final int RESULTS_PER_PAGE = 10;

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
            if(account.get().getOwnedRooms().size() >= MAX_CHATROOM_PER_USER) {
                return new ResponseEntity<>("You can not create more than 15 rooms. Please delete some before creating one.", HttpStatus.BAD_REQUEST);
            }
            Chatroom chatroom =  new Chatroom(null, chatroomRequestBody.getName(), LocalDate.now(), account.get());
            logger.info(account.get().toString());
            chatroomRepository.save(chatroom);
            accountRepository.save(account.get());
            return new ResponseEntity<>("Successfully created room", HttpStatus.CREATED);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    @GetMapping("/chatroom")
    public ResponseEntity<Set<ChatroomResponse>> getRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, RESULTS_PER_PAGE);
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

    @GetMapping("/users/{username}/CreatedChatrooms")
    public ResponseEntity<Set<ChatroomResponse>> getCreatedChatrooms(@PathVariable String username, @RequestParam int page) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()) {
            Pageable pageable = PageRequest.of(page, RESULTS_PER_PAGE);
            List<Chatroom> ownedChatrooms = chatroomRepository.findByOwnerOrderByIdDesc(account.get(), pageable);
            Set<ChatroomResponse> ownedChatroomsResponse = chatroomUtil.convertChatroomToChatroomResponse(ownedChatrooms);
            return new ResponseEntity<>(ownedChatroomsResponse, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    @GetMapping("/users/{username}/chatrooms")
    public ResponseEntity<Set<ChatroomResponse>> getChatrooms(@PathVariable String username, @RequestParam int page) {
        Account account = accountRepository.findByUsername(username).orElse(null);
        if(account == null) { throw new UsernameNotFoundException("User not found"); }
        Pageable pageable = PageRequest.of(page, RESULTS_PER_PAGE);
        List<Chatroom> chatrooms = chatroomRepository.findChatroomsByAccounts_Id(account.getId(), pageable);
        Set<ChatroomResponse> chatroomResponses = chatroomUtil.convertChatroomToChatroomResponse(chatrooms);

        return new ResponseEntity<>(chatroomResponses, HttpStatus.OK);
    }

    @DeleteMapping("/chatroom/{id}")
    public ResponseEntity<String> deleteChatroom(@PathVariable int id) {
        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);
        Chatroom chatroom = optionalChatroom.orElseThrow(() -> new UsernameNotFoundException("Chatroom of id: " + id + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication.toString());
        Optional<Account> optionalAccount = accountRepository.findByUsername(authentication.getName());
        Account account = optionalAccount.orElseThrow(() -> new UsernameNotFoundException("Unable to find chatroom owner"));

        if(account.getUsername().equals(chatroom.getOwner().getUsername())) {
            chatroomRepository.deleteFromAccountChatroomWithChatroomId(chatroom.getId());
            chatroomRepository.deleteById(chatroom.getId());
            logger.info("Deleted {}'s chatroom of id: {}", authentication.getName(), chatroom.getId());
            return new ResponseEntity<>("Successfully deleted chatroom", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You must own the chatroom in order to delete it", HttpStatus.BAD_REQUEST);
        }
    }
}