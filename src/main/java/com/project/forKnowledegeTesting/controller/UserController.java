package com.project.forKnowledegeTesting.controller;

import com.project.forKnowledegeTesting.dto.UserDTO;
import com.project.forKnowledegeTesting.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<UserDTO> user() {
     return userService.getAllUser();
    }
}
