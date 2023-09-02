package com.project.chatroom.message;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.room.Chatroom;
import com.project.chatroom.room.ChatroomRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageResource {

    private final AccountRepository accountRepository;
    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public MessageResource(AccountRepository accountRepository, ChatroomRepository chatroomRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.chatroomRepository = chatroomRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/chatroom/{id}/message")
    public ResponseEntity<String> createMessage(@PathVariable int id, @RequestBody @Valid MessageRequest messageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        Account account = optionalAccount.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);
        Chatroom chatroom = optionalChatroom.orElseThrow(() -> new UsernameNotFoundException("Chatroom not found"));

        boolean joined = accountRepository.findAccountFromAccountChatroom(id, account.getId()).isPresent();
        if(joined) {
            logger.info("Creating a message for {} from {}", chatroom.getName(), account.getUsername());
            Message message = new Message(null, account, chatroom, messageRequest.message());
            messageRepository.save(message);
            return new ResponseEntity<>("Successfully posted message", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You must be in the room in order to post a message there", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/chatroom/{id}/message")
    public ResponseEntity<List<MessageResponse>> getChatroomMessage(@PathVariable int id) {

        Optional<Chatroom> optionalChatroom = chatroomRepository.findById(id);
        Chatroom chatroom = optionalChatroom.orElseThrow(() -> new UsernameNotFoundException("Chatroom not found"));

        List<Message> messages = messageRepository.getMessagesByChatroomIdOrderByLocalDateTimeDesc(chatroom.getId());
        List<MessageResponse> messageResponses = messages.stream().map(message ->
                new MessageResponse(message.getAccount().getUsername(), message.getMessage(), message.getLocalDateTime().toString())).toList();

        return new ResponseEntity<>(messageResponses, HttpStatus.OK);
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        logger.info(messageRequest.toString());
        return new ResponseEntity<>(messageRequest.message(), HttpStatus.OK);
//        throw new UsernameNotFoundException("test");
//        return messageRequest.message();
    }
}
