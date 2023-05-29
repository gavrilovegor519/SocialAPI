package com.egor.socialapi.repos;

import com.egor.socialapi.entities.Posts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepo extends CrudRepository<Posts, Long>, PagingAndSortingRepository<Posts, Long> {

}
