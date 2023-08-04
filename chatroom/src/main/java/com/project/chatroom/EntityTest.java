package com.project.chatroom;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import com.project.chatroom.account.Role;
import com.project.chatroom.room.Chatroom;
import com.project.chatroom.room.ChatroomRepository;
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

    public EntityTest(AccountRepository accountRepository, PasswordEncoder passwordEncoder, ChatroomRepository chatroomRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.chatroomRepository = chatroomRepository;
    }

    @Bean
    public void initialize() {
        Account account = addAccount("thedog", "thecat");

        addChatroom("Cool room", account);
        addChatroom("Room test", account);
        addChatroom("Cats!", account);
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
