package com.project.chatroom.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    List<Account> findAccountsByChatroomsId(Integer chatrooms_id);

    List<Account> findAccountsByChatrooms_Id(Integer chatrooms_id);

    @Query(value = "SELECT * FROM account_chatroom WHERE chatroom_id = :chatroom_id AND account_id = :account_id", nativeQuery = true)
    Optional<Object> findAccountFromAccountChatroom(
            @Param("chatroom_id") int chatroom_id,
            @Param("account_id") int account_id);
}
