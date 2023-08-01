package com.project.chatroom.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer> {

    @Query(value = "SELECT * FROM chatroom ORDER BY date_created", nativeQuery = true)
    List<Chatroom> getChatroomsByDateCreated();
}
