package com.project.chatroom.account;

import com.project.chatroom.jwt.JwtResponse;
import com.project.chatroom.jwt.JwtTokenService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountResource {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    Logger logger = LoggerFactory.getLogger(AccountResource.class);
    public AccountResource(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
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
}
