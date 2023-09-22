package com.project.chatroom.message;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> getMessagesByChatroomIdOrderByLocalDateTimeDesc(Integer chatroom_id, Pageable pageable);
}
