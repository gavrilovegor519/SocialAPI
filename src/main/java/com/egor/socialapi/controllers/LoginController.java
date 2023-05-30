package com.egor.socialapi.controllers;

import com.egor.socialapi.converters.UserToUserDTOConverter;
import com.egor.socialapi.dto.LoginDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.repos.UserRepo;
import com.egor.socialapi.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService, UserRepo userRepo, UserToUserDTOConverter userToUserDTOConverter) {
        this.userService = userService;
    }

    @PostMapping("/reg")
    public void reg(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.authenticate(loginDTO);
    }
}
