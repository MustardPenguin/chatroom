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

    public Message(Integer id, Account account, Chatroom chatroom, String message) {
        this.id = id;
        this.account = account;
        this.chatroom = chatroom;
        this.localDateTime = LocalDateTime.now();
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", account=" + account +
                ", chatroom=" + chatroom +
                ", localDateTime=" + localDateTime +
                ", message='" + message + '\'' +
                '}';
    }
}
