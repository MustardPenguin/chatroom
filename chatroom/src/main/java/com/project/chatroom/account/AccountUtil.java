package com.project.chatroom.account;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountUtil {

    public List<AccountResponse> convertAccountToAccountResponse(List<Account> accounts) {
        return accounts.stream().map(account -> new AccountResponse(
                account.getId(),
                account.getUsername()
        )).toList();
    }
}
