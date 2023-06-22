package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.PostPageToPostPageDTOConverter;
import com.egor.socialapi.converters.PostsDTOToPostsConverter;
import com.egor.socialapi.converters.PostsToPostsDTOConverter;
import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Friendship;
import com.egor.socialapi.entities.Posts;
import com.egor.socialapi.entities.User;
import com.egor.socialapi.repos.FriendshipRepo;
import com.egor.socialapi.repos.PostsRepo;
import com.egor.socialapi.services.PostsService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepo postsRepo;
    private final FriendshipRepo friendshipRepo;
    private final PostsDTOToPostsConverter postsDTOToPostsConverter;
    private final PostPageToPostPageDTOConverter postPageToPostPageDTOConverter;
    private final PostsToPostsDTOConverter postsToPostsDTOConverter;

    @Override
    public void createPost(PostsDTO postCreateBindingModel) throws Exception {
        Posts post = postsDTOToPostsConverter.convert(postCreateBindingModel);
        assert post != null;
        postsRepo.save(post);
    }

    @Override
    public PageDTO<PostsDTO> getAllPosts(Long userId, Pageable pageable) {
        List<Friendship> friendships = friendshipRepo.findAllByUserSenderId(userId);
        List<User> friendsList = new ArrayList<>();
        friendships.forEach(e -> friendsList.add(e.getUserReceiver()));
        return postPageToPostPageDTOConverter.convert(postsRepo.findPostsByLoggedInUserIn(friendsList, pageable));
    }

    @Override
    public void deletePost(Long postToRemoveId) throws Exception {
        postsRepo.deleteById(postToRemoveId);
    }

    @Override
    public PostsDTO getPostById(Long postId) throws Exception {
        return postsToPostsDTOConverter.convert(postsRepo.findById(postId).get());
    }
}
