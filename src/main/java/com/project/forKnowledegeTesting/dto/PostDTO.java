package com.project.forKnowledegeTesting.dto;

import java.time.LocalDateTime;

public record PostDTO(
        Integer id,
        String title,
        String content,
        LocalDateTime createdAt
) {
}
