package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer> {

    @Query(value = "SELECT * FROM chatroom ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Chatroom> findAllOrderedByIdDesc();

    List<Chatroom> findChatroomByOwnerNotNullOrderByIdDesc(Pageable pageable);

    List<Chatroom> findByOwnerOrderByIdDesc(Account account, Pageable pageable);

    List<Chatroom> findChatroomsByAccounts_Id(Integer accounts_id, Pageable pageable);
}
