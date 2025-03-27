package com.project.forKnowledegeTesting.service;

import com.project.forKnowledegeTesting.dto.UserDTO;
import com.project.forKnowledegeTesting.mapper.UserMapper;
import com.project.forKnowledegeTesting.models.User;
import com.project.forKnowledegeTesting.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public List<UserDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();

    }





}
