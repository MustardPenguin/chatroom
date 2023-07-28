package com.project.chatroom.message;

import com.project.chatroom.account.Account;
import com.project.chatroom.room.Chatroom;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chatroom chatroom;

    private LocalDateTime localDateTime;

    private String message;

    public Message() {

    }

    public Message(Integer id, Account account, Chatroom chatroom, LocalDateTime localDateTime, String message) {
        this.id = id;
        this.account = account;
        this.chatroom = chatroom;
        this.localDateTime = localDateTime;
        this.message = message;
    }


}
