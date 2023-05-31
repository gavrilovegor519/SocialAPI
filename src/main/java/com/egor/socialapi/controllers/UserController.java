package com.egor.socialapi.controllers;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
