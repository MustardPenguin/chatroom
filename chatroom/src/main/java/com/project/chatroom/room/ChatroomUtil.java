package com.project.chatroom.room;

import com.project.chatroom.account.Account;
import com.project.chatroom.account.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChatroomUtil {

    private final AccountRepository accountRepository;

    public ChatroomUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Set<ChatroomResponse> convertChatroomToChatroomResponse(Collection<Chatroom> chatrooms) {
        Set<ChatroomResponse> chatroomResponses = new HashSet<>();

        for(Chatroom chatroom: chatrooms) {
            int members = accountRepository.findAccountsByChatroomsId(chatroom.getId()).size();
            ChatroomResponse chatroomResponse = new ChatroomResponse(chatroom, members);
            chatroomResponses.add(chatroomResponse);
        }

        return chatroomResponses;
    }
}
