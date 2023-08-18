package com.project.chatroom;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.account.Role;
import com.project.chatroom.jwt.JwtTokenService;
import com.project.chatroom.room.Chatroom;
import com.project.chatroom.room.ChatroomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

// Creates a new account at startup
@Service
public class EntityTest {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChatroomRepository chatroomRepository;
    private final JwtTokenService jwtTokenService;

    Logger logger = LoggerFactory.getLogger(EntityTest.class);

    public EntityTest(AccountRepository accountRepository, PasswordEncoder passwordEncoder, ChatroomRepository chatroomRepository, JwtTokenService jwtTokenService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.chatroomRepository = chatroomRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public void initialize() {
        Account account = addAccount("thedog", "thecat");
        addAccount("thecat", "thedog");

        String token = jwtTokenService.generateToken(account);
        logger.info("Bearer " + token);

        addChatroom("Cool room", account);
        addChatroom("Room test", account);
        addChatroom("Cats!", account);
        addChatroom("Room 4", account);
        addChatroom("Room number 5", account);
        addChatroom("Pagination stuff", account);
        addChatroom("More rooms!", account);
        addChatroom("Dogs!", account);
        addChatroom("10 rooms at least", account);
        addChatroom("Rock and stone!", account);
        addChatroom("Oneorangebraincell", account);
        addChatroom("12222", account);
        addChatroom("Food", account);
        addChatroom("Name", account);

        accountRepository.save(account);
    }

    public Account addAccount(String name, String password) {
        Account account = new Account(
                null,
                name,
                passwordEncoder.encode(password),
                Role.USER
        );
        accountRepository.save(account);

        return account;
    }

    public Chatroom addChatroom(String name, Account owner) {
        Chatroom chatroom = new Chatroom(
                null,
                name,
                LocalDate.now(),
                owner
        );
        chatroomRepository.save(chatroom);
        return chatroom;
    }
}
