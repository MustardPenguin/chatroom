package com.project.chatroom.message;

import java.time.LocalDateTime;

public record MessageResponse(String username, String message, LocalDateTime localDateTime) {
}
