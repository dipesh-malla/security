package com.project.forKnowledegeTesting.dto;


import com.project.forKnowledegeTesting.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private Set<String> role;
    private List<PostDTO> posts;

}

