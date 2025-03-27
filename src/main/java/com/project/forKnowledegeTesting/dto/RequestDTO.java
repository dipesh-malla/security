package com.project.forKnowledegeTesting.dto;

import jakarta.persistence.Entity;
import lombok.Data;


@Data
public class RequestDTO {
    private String username;
    private String password;
}
