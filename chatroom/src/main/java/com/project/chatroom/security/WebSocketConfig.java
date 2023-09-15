package com.project.chatroom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.messaging.access.intercept.AuthorizationChannelInterceptor;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat").setAllowedOrigins("*");
//        registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:4200/").withSockJS();
        registry
                .addEndpoint("/chat")
//                .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
//                .setHandshakeHandler(new WebSocketHandshakeHandler())
                .withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // https://github.com/oktadev/okta-java-websockets-example/blob/main/src/main/java/com/okta/developer/websockets/WebSocketAuthenticationConfig.java
        // https://docs.spring.io/spring-framework/reference/web/websocket/stomp/authentication-token-based.html

        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("intercept");
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if(StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
//                    System.out.println(accessor.toString());
                    System.out.println("Authorization: " + authorization);
//                    String accessToken = authorization.get(0).split(" ")[1];


//                    Jwt jwt = jwtDecoder.decode(accessToken);
//                    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//                    Authentication authentication = converter.convert(jwt);
//                    accessor.setUser(authentication);
                }
                return ChannelInterceptor.super.preSend(message, channel);
            }
        });
    }

    // Disable CRSF
//    @Bean(name = "csrfChannelInterceptor")
//    ChannelInterceptor csrfChannelInterceptor() {
//        return new ChannelInterceptor() {};
//    }
}
