package com.project.chatroom.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.chatroom.account.Account;
import com.project.chatroom.message.Message;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Chatroom {


    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "chatroom")
    @JsonIgnore
    private List<Message> messages;

//    @OneToMany(mappedBy = "chatroom")
//    private List<Account> accounts;

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
