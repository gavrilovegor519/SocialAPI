package com.egor.socialapi.converters;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Posts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PostPageToPostPageDTOConverter implements Converter<Page<Posts>, PageDTO<PostsDTO>> {
    private final PostsToPostsDTOConverter postsToPostsDTOConverter;

    public PostPageToPostPageDTOConverter(PostsToPostsDTOConverter postsToPostsDTOConverter) {
        this.postsToPostsDTOConverter = postsToPostsDTOConverter;
    }

    @Override
    public PageDTO<PostsDTO> convert(Page<Posts> page) {
        List<PostsDTO> list = page.getContent().stream()
                .map(postsToPostsDTOConverter::convert)
                .collect(Collectors.toList());

        return PageDTO.<PostsDTO>builder()
                .content(list)
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
