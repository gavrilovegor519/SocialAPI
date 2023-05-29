package com.egor.socialapi.services;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;

public interface PostsService {
    void createPost(PostsDTO postCreateBindingModel) throws Exception;

    PageDTO<PostsDTO> getAllPosts();

    void deletePost(Long postToRemoveId) throws Exception;
}
