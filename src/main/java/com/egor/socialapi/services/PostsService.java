package com.egor.socialapi.services;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;
import org.springframework.data.domain.Pageable;

public interface PostsService {
    void createPost(PostsDTO postCreateBindingModel) throws Exception;

    PageDTO<PostsDTO> getAllPosts(Long userId, Pageable pageable);

    void deletePost(Long postToRemoveId) throws Exception;
}
