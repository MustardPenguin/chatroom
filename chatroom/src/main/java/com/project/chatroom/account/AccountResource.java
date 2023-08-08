package com.project.chatroom.account;

import com.project.chatroom.jwt.JwtResponse;
import com.project.chatroom.jwt.JwtTokenService;
import com.project.chatroom.room.Chatroom;
import com.project.chatroom.room.ChatroomResponse;
import com.project.chatroom.room.ChatroomUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AccountResource {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ChatroomUtil chatroomUtil;

    Logger logger = LoggerFactory.getLogger(AccountResource.class);
    public AccountResource(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, ChatroomUtil chatroomUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.chatroomUtil = chatroomUtil;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AccountRequestBody accountRequestBody) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            accountRequestBody.getUsername(),
                            accountRequestBody.getPassword()
                    )
            );
        } catch(AuthenticationException authenticationException) {
//            throw new UsernameNotFoundException("Incorrect credentials");
            return new JwtResponse("Error: Incorrect credentials");
        }

        Account account = accountRepository.findByUsername(accountRequestBody.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String jwtToken = jwtTokenService.generateToken(account);
        return new JwtResponse(jwtToken);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AccountRequestBody accountRequestBody, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<String>();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        Optional<Account> findUser = accountRepository.findByUsername(accountRequestBody.getUsername());
        if(findUser.isPresent()) {
            return new ResponseEntity<String>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(accountRequestBody.getPassword());
        Account account = new Account(
                null,
                accountRequestBody.getUsername(),
                encodedPassword,
                Role.USER
        );
        logger.info(account.toString());
        accountRepository.save(account);

        return new ResponseEntity<String>("Successfully created account", HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}/CreatedChatrooms")
    public ResponseEntity<Set<ChatroomResponse>> getCreatedChatrooms(@PathVariable String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()) {
            Set<Chatroom> ownedRooms = account.get().getOwnedRooms();
            Set<ChatroomResponse> ownedChatroomResponses = chatroomUtil.convertChatroomToChatroomResponse(ownedRooms);

            return new ResponseEntity<>(ownedChatroomResponses, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    @GetMapping("/users/{username}/chatrooms")
    public void getChatrooms(@PathVariable String username) {

    }
}
