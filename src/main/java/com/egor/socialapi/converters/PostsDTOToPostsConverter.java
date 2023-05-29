package com.egor.socialapi.converters;

import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Posts;
import org.springframework.core.convert.converter.Converter;

public class PostsDTOToPostsConverter implements Converter<PostsDTO, Posts> {
    @Override
    public Posts convert(PostsDTO source) {
        Posts.PostsBuilder builder = Posts.builder()
                .id(source.getId())
                .content(source.getContent())
                .imageUrl(source.getImageUrl())
                .loggedInUser(source.getLoggedInUser())
                .time(source.getTime());

        return builder.build();
    }
}
