package com.project.chatroom.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.chatroom.account.AccountRepository;

import java.time.LocalDate;

public class ChatroomResponse {

    private Integer id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateCreated;

    private String owner;

    private int members;
    private boolean joined;

    public ChatroomResponse(Chatroom chatroom, int members) {
        this.id = chatroom.getId();
        this.name = chatroom.getName();
        this.dateCreated = chatroom.getDateCreated();
        this.owner = chatroom.getOwner().getUsername();
        this.members = members;
        this.joined = false;
    }

    public ChatroomResponse(Chatroom chatroom, int members, boolean joined) {
        this.id = chatroom.getId();
        this.name = chatroom.getName();
        this.dateCreated = chatroom.getDateCreated();
        this.owner = chatroom.getOwner().getUsername();
        this.members = members;
        this.joined = joined;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }
}
