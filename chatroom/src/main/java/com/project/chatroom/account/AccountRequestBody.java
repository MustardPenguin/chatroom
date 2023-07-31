package com.project.chatroom.account;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountRequestBody {

    @Size(min=3, max=20, message="Please input at least 3 characters for username")
    @Pattern(regexp="^[a-zA-Z0-9]*$", message="Please input a valid character for the username")
    private String username;

    @Size(min=3, max=20, message="Please input between 3 and 20 password")
    @Pattern(regexp="^[a-zA-Z0-9]*$", message="Please input a valid character for the password")
    private String password;

    public AccountRequestBody() {

    }

    public AccountRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountRequestBody{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
