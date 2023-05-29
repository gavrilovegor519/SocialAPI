package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.PageToPageDTOUserConverter;
import com.egor.socialapi.converters.PostsDTOToPostsConverter;
import com.egor.socialapi.converters.PostsToPostsDTOConverter;
import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Posts;
import com.egor.socialapi.repos.PostsRepo;
import com.egor.socialapi.services.PostsService;

public class PostsServiceImpl implements PostsService {
    private final PostsRepo postsRepo;
    private final PostsDTOToPostsConverter postsDTOToPostsConverter;
    private final PostsToPostsDTOConverter postsToPostsDTOConverter;
    private final PageToPageDTOUserConverter pageToPageDTOUserConverter;

    public PostsServiceImpl(PostsRepo postsRepo, PostsDTOToPostsConverter postsDTOToPostsConverter, PostsToPostsDTOConverter postsToPostsDTOConverter, PageToPageDTOUserConverter pageToPageDTOUserConverter) {
        this.postsRepo = postsRepo;
        this.postsDTOToPostsConverter = postsDTOToPostsConverter;
        this.postsToPostsDTOConverter = postsToPostsDTOConverter;
        this.pageToPageDTOUserConverter = pageToPageDTOUserConverter;
    }

    @Override
    public void createPost(PostsDTO postCreateBindingModel) throws Exception {
        Posts post = postsDTOToPostsConverter.convert(postCreateBindingModel);
        assert post != null;
        postsRepo.save(post);
    }

    //TODO: Add get all posts of user's friends.
    @Override
    public PageDTO<PostsDTO> getAllPosts() {
        return null;
    }

    @Override
    public void deletePost(Long postToRemoveId) throws Exception {
        postsRepo.deleteById(postToRemoveId);
    }
}
