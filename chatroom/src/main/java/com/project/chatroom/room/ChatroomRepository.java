package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer> {

    @Query(value = "SELECT * FROM chatroom ORDER BY id DESC", nativeQuery = true)
    List<Chatroom> findAllOrderedByIdDesc();


}
