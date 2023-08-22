package com.project.chatroom.account;

import java.util.List;

public class AccountResponse {

    private int id;
    private String name;


    public AccountResponse(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "AccountResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
