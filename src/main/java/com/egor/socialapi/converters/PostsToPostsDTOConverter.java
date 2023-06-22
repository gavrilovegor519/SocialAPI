package com.egor.socialapi.converters;

import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Posts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostsToPostsDTOConverter implements Converter<Posts, PostsDTO> {
    @Override
    public PostsDTO convert(Posts source) {
        return PostsDTO.builder()
                .id(source.getId())
                .loggedInUser(source.getLoggedInUser())
                .imageUrl(source.getImageUrl())
                .time(source.getTime())
                .content(source.getContent())
                .build();
    }
}
