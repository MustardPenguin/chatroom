package com.project.chatroom.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.chatroom.message.Message;
import com.project.chatroom.room.Chatroom;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account implements UserDetails {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
//    @JsonIgnore
    @JsonBackReference
    private Set<Chatroom> ownedRooms;

//    @OneToMany(mappedBy = "account")
//    @JsonIgnore
//    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "account_chatroom",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
    )
    private Set<Chatroom> chatrooms;

    public Account() {}

    public Account(Integer id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.chatrooms = new HashSet<>();
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Chatroom> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(Set<Chatroom> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }

    public Set<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(Set<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
