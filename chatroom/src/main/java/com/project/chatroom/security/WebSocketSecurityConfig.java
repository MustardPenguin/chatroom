package com.project.chatroom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

//@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages

//                .nullDestMatcher().authenticated()
//                .simpSubscribeDestMatchers("/topic/message").permitAll()
//                .simpDestMatchers("/topic/**").permitAll()
//                .simpDestMatchers("/chat").permitAll()
//                .simpSubscribeDestMatchers("/topic/**").permitAll()
//                .simpSubscribeDestMatchers("/chat").permitAll()
                .simpTypeMatchers(SimpMessageType.CONNECT,
                        SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
//                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
//                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
//                .nullDestMatcher().authenticated()
                .anyMessage().permitAll();

        return messages.build();
    }
}
