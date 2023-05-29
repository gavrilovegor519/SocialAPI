package com.egor.socialapi.services;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.entities.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getUser(Long id);

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    PageDTO<UserDTO> findAllPageable(Long id, Pageable pageable);

    PageDTO<UserDTO> findAllWithSearch(Long id, String search, Pageable pageable);

    void createUser(UserDTO userDTO);

    void updatePassword(String password, Long id);

    void makeUserAdmin(Long userId);

    void blockUser(Long userId);
}
