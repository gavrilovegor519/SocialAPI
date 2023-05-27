package com.egor.socialapi.repos;

import com.egor.socialapi.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepo extends JpaRepository<Posts, String> {

    List<Posts> findAllByTimelineUserOrderByTimeDesc(User timelineUser);
}
