package com.egor.socialapi.converters;

import com.egor.socialapi.dto.MessageDTO;
import com.egor.socialapi.entities.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDTOToMessageConverter implements Converter<MessageDTO, Message> {

    private final UserDTOToUserConverter userDtoToUserConverter;

    @Override
    public Message convert(MessageDTO messageDTO) {

        return Message.builder()
                .time(messageDTO.getTime())
                .message(messageDTO.getMessage())
                .sender(userDtoToUserConverter.convert(messageDTO.getSender()))
                .receiver(userDtoToUserConverter.convert(messageDTO.getReceiver()))
                .build();
    }
}
