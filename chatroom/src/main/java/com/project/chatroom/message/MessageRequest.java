package com.project.chatroom.message;

import jakarta.validation.constraints.Size;

public record MessageRequest(
        @Size(min = 1, max = 50, message = "Please input between 1 and 50 character") String message,
        String token
) { }