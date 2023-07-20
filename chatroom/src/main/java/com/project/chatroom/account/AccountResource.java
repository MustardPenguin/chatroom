package com.project.chatroom.account;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountResource {
    AccountRepository accountRepository;
    Logger logger = LoggerFactory.getLogger(AccountResource.class);
    public AccountResource(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/login")
    public void login() {

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid Account account, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<String>();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        Account findUser = accountRepository.findByUsername(account.getUsername());
        if(findUser != null) {
            return new ResponseEntity<String>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        logger.info(account.toString());
        accountRepository.save(account);

        return new ResponseEntity<String>("Successfully created account", HttpStatus.CREATED);
    }
}
