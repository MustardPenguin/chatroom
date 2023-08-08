package com.project.chatroom.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    List<Account> findAccountsByChatroomsId(Integer chatrooms_id);
}
