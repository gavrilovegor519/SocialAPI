package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.MessageDTOToMessageConverter;
import com.egor.socialapi.converters.MessageToMessageDTOConverter;
import com.egor.socialapi.dto.MessageDTO;
import com.egor.socialapi.entities.Message;
import com.egor.socialapi.entities.User;
import com.egor.socialapi.repos.MessageRepo;
import com.egor.socialapi.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepository;
    private final MessageToMessageDTOConverter messageToMessageDtoConverter;
    private final MessageDTOToMessageConverter messageDtoToMessageConverter;

    @Override
    @Transactional(readOnly = true)
    public Collection<MessageDTO> findAllRecentMessages(Long id) {
        Iterable<Message> all = messageRepository.findAllRecentMessages(id);
        Map<User, MessageDTO> map = new HashMap<>();
        all.forEach(m -> {
            MessageDTO messageDTO = messageToMessageDtoConverter.convert(m, id);
            User user = m.getSender().getId().equals(id) ? m.getReceiver() : m.getSender();
            map.put(user, messageDTO);
        });
        return map.values();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> findConversation(Long userId, Long companionId) {
        List<Message> all = messageRepository.findConversation(userId, companionId);
        List<MessageDTO> messages = new LinkedList<>();
        all.forEach(m -> messages.add(messageToMessageDtoConverter.convert(m, userId)));
        return messages;
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO getRecentMessage(Long id) {
        Message message = messageRepository.findFirstBySenderIdOrReceiverIdOrderByIdDesc(id, id);
        return messageToMessageDtoConverter.convert(message, id);
    }

    @Override
    @Transactional
    public void postMessage(MessageDTO messageDTO) {
        Message message = messageDtoToMessageConverter.convert(messageDTO);
        assert message != null;
        messageRepository.save(message);
    }

}
