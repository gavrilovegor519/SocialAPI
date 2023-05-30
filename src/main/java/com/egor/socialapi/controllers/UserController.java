package com.egor.socialapi.controllers;

import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public PageDTO<UserDTO> all(@RequestParam(name = "first") int first,
                                @RequestParam(name = "last") int last) {
        return userService.findAllPageable(null, PageRequest.of(first, last - first, Sort.by("username")));
    }

    @GetMapping("/search")
    public PageDTO<UserDTO> search(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "first") int first,
                                   @RequestParam(name = "last") int last) {
        return userService.findAllWithSearch(null, name, PageRequest.of(first, last - first, Sort.by("username")));
    }
}
