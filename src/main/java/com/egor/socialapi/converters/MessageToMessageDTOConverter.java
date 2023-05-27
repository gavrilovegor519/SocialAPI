package com.egor.socialapi.converters;

import com.egor.socialapi.dto.MessageDTO;
import com.egor.socialapi.entities.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageToMessageDTOConverter {

    private final UserToUserDTOConverter userToUserDtoConverter;

    public MessageDTO convert(Message message, Long id) {
        if(message == null) {
            return null;
        }
        return MessageDTO.builder()
                .time(message.getTime())
                .message(message.getMessage())
                .receiver(userToUserDtoConverter.convert(message.getReceiver()))
                .sender(userToUserDtoConverter.convert(message.getSender()))
                .companionId(id.equals(message.getSender().getId()) ?
                        message.getReceiver().getId() : message.getSender().getId())
                .build();
    }
}
