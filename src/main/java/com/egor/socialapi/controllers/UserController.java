package com.egor.socialapi.controllers;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/users/all")
    public PageDTO<UserDTO> all(@RequestParam(name = "first") int first,
                                @RequestParam(name = "last") int last) {
        return userService.findAllPageable(PageRequest.of(first, last - first));
    }

    @GetMapping("/user/users/search")
    public PageDTO<UserDTO> search(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "first") int first,
                                   @RequestParam(name = "last") int last) {
        return userService.findAllWithSearch(name, PageRequest.of(first, last - first));
    }

    @PostMapping("/user/users/changePassword")
    public void changePassword(@RequestBody String password, Authentication authentication) {
        UserDTO user = userService.getUserByUsername(authentication.getName());
        userService.updatePassword(password, user.getId());
    }

    @GetMapping("/user/users/getById/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/users/getByEmail")
    public UserDTO getByEmail(@RequestParam(name = "email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/user/users/getByUsername")
    public UserDTO getByUsername(@RequestParam(name = "username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/admin/users/makeUserAdmin/{id}")
    public void makeUserAdmin(@PathVariable Long id) {
        userService.makeUserAdmin(id);
    }

    @PostMapping("/admin/users/blockUser/{id}")
    public void blockUser(@PathVariable Long id) {
        userService.blockUser(id);
    }
}
