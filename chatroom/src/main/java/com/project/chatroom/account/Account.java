package com.project.chatroom.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=3, max=30, message="Please input at least 3 characters for username")
    @Pattern(regexp="^[a-zA-Z0-9]*$", message="Please input a valid character for the username")
    private String username;

    @Size(min=3, max=30, message="Please input at least 3 characters for password")
    @Pattern(regexp="^[a-zA-Z0-9]*$", message="Please input a valid character for the password")
    private String password;

    public Account() {

    }

    public Account(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
