package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.PostPageToPostPageDTOConverter;
import com.egor.socialapi.converters.PostsDTOToPostsConverter;
import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.PostsDTO;
import com.egor.socialapi.entities.Friendship;
import com.egor.socialapi.entities.Posts;
import com.egor.socialapi.entities.User;
import com.egor.socialapi.repos.FriendshipRepo;
import com.egor.socialapi.repos.PostsRepo;
import com.egor.socialapi.services.PostsService;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PostsServiceImpl implements PostsService {
    private final PostsRepo postsRepo;
    private final FriendshipRepo friendshipRepo;
    private final PostsDTOToPostsConverter postsDTOToPostsConverter;
    private final PostPageToPostPageDTOConverter postPageToPostPageDTOConverter;

    public PostsServiceImpl(PostsRepo postsRepo, FriendshipRepo friendshipRepo, PostsDTOToPostsConverter postsDTOToPostsConverter, PostPageToPostPageDTOConverter postPageToPostPageDTOConverter) {
        this.postsRepo = postsRepo;
        this.friendshipRepo = friendshipRepo;
        this.postsDTOToPostsConverter = postsDTOToPostsConverter;
        this.postPageToPostPageDTOConverter = postPageToPostPageDTOConverter;
    }

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
}
