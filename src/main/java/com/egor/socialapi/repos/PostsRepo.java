package com.egor.socialapi.repos;

import com.egor.socialapi.entities.Posts;
import com.egor.socialapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface PostsRepo extends CrudRepository<Posts, Long>, PagingAndSortingRepository<Posts, Long> {
    Page<Posts> findPostsByLoggedInUserIn(Collection<User> loggedInUser, Pageable pageable);
}
