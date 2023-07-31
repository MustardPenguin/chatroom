package com.project.chatroom.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.chatroom.account.Account;
import com.project.chatroom.message.Message;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Chatroom {


    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "chatroom")
    @JsonIgnore
    private List<Message> messages;

    @ManyToMany(mappedBy = "chatrooms")
    private Set<Account> accounts;

    public Chatroom() {

    }

    public Chatroom(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

//    public List<Account> getAccounts() {
//        return accounts;
//    }
//
//    public void setAccounts(List<Account> accounts) {
//        this.accounts = accounts;
//    }
}
