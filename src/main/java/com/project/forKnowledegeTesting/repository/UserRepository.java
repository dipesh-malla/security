package com.project.forKnowledegeTesting.repository;

import com.project.forKnowledegeTesting.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);
}
