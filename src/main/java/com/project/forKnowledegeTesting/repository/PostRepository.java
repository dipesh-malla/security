package com.project.forKnowledegeTesting.repository;

import com.project.forKnowledegeTesting.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
