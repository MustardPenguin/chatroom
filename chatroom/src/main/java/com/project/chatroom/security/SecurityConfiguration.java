package com.project.chatroom.security;

import com.project.chatroom.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/authenticate").permitAll()
                        .anyRequest().authenticated()

        );
        http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());
        http.headers(header ->
                header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);
//        http.oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
