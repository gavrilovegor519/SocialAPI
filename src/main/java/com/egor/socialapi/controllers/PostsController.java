package com.egor.socialapi.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.egor.socialapi.converters.*;
import com.egor.socialapi.dto.*;
import com.egor.socialapi.entities.*;
import com.egor.socialapi.services.*;

@RestController
public class PostsController {
    private final PostsService postsService;
    private final UserService userService;
    private final UserDTOToUserConverter userDTOToUserConverter;

    public PostsController(PostsService postsService, UserService userService, UserDTOToUserConverter userDTOToUserConverter) {
            this.postsService = postsService;
            this.userService = userService;
            this.userDTOToUserConverter = userDTOToUserConverter;
    }

    @GetMapping("/user/posts/all")
    public PageDTO<PostsDTO> getAllPosts(@RequestParam(name = "first") int first,
                                        @RequestParam(name = "last") int last,
                                        Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return postsService.getAllPosts(userId, PageRequest.of(first, last - first));
    }

    @GetMapping("/user/posts/post/{postId}")
    public PostsDTO getPostById(@PathVariable Long postId) throws Exception {
        return postsService.getPostById(postId);
    }

    @PostMapping("/user/posts/create")
    public void createPost(@RequestBody PostsDTO postCreateBindingModel, Authentication authentication) throws Exception {
        if (postCreateBindingModel.getLoggedInUser() == null 
            || authentication.getName() != postCreateBindingModel.getLoggedInUser().getUsername()) {
            String username = authentication.getName();
            UserDTO userDTO = userService.getUserByUsername(username);
            User user = userDTOToUserConverter.convert(userDTO);
            postCreateBindingModel.setLoggedInUser(user);
        }
        postsService.createPost(postCreateBindingModel);
    }

    @DeleteMapping("/user/posts/delete/{postId}")
    public void deletePost(@PathVariable Long postId, Authentication authentication) throws Exception {
        String username = userService.getUserByUsername(authentication.getName()).getUsername();
        if (postsService.getPostById(postId).getLoggedInUser().getUsername() == username) {
            postsService.deletePost(postId);
        }
    }
}
