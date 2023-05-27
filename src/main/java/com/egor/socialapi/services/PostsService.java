package com.egor.socialapi.services;

import com.egor.socialapi.dto.PostsDTO;

import java.util.List;

public interface PostsService {
    boolean createPost(PostsDTO postCreateBindingModel) throws Exception;

    List<PostsDTO> getAllPosts(String timelineUserId);

    boolean deletePost(String loggedInUserId, String postToRemoveId) throws Exception;
}
