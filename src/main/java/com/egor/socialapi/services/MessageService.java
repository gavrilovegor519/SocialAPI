package com.egor.socialapi.services;

import com.egor.socialapi.dto.MessageDTO;

import java.util.Collection;
import java.util.List;

public interface MessageService {
    Collection<MessageDTO> findAllRecentMessages(Long id);

    List<MessageDTO> findConversation(Long userId, Long companionId);

    MessageDTO getRecentMessage(Long id);

    void postMessage(MessageDTO messageDTO);
}
