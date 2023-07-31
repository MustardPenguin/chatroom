package com.project.chatroom.room;

import jakarta.validation.constraints.Size;

public class ChatroomRequestBody {

    @Size(min = 3, max = 20, message = "Please enter between 3 and 20 characters.")
    private String name;

    public ChatroomRequestBody() {

    }

    public ChatroomRequestBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatroomRequestBody{" +
                "name='" + name + '\'' +
                '}';
    }
}
