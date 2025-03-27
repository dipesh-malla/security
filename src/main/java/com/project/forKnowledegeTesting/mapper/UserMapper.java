package com.project.forKnowledegeTesting.mapper;

import com.project.forKnowledegeTesting.dto.PostDTO;
import com.project.forKnowledegeTesting.dto.RegisterDTO;
import com.project.forKnowledegeTesting.dto.UserDTO;
import com.project.forKnowledegeTesting.models.Role;
import com.project.forKnowledegeTesting.models.User;
import com.project.forKnowledegeTesting.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDTO toUserDTO(User user){
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        List<PostDTO> postDTOs = user.getPosts().stream()
                .map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent(),post.getCreatedAt()))
                .toList();
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                null,
                roles,
                postDTOs
        );
    };



    public User toUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));
        user.setRoles(Set.of(userRole));
        return user;
    }



}
