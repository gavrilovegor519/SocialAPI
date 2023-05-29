package com.egor.socialapi.repos;

import com.egor.socialapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    User findUserByEmail(String email);

    Page<User> findAllByIdNot(Long Id, Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.id <> :id AND LOWER(u.username) LIKE :search")
    Page<User> findAllWithSearch(@Param("id") Long id, @Param("search") String search, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);
}
